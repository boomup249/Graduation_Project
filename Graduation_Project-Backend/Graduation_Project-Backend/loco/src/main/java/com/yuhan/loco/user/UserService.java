package com.yuhan.loco.user;

import java.sql.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   UserDB user = new UserDB();
   
   public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
   public String encodePWD(String pwd) {
      String encodePWD = passwordEncoder.encode(pwd);
      return encodePWD;
   }
    public boolean PWDMatches(String pwd) {
       String encodepwd = user.getPWD();
       boolean pwdcheck = passwordEncoder.matches(pwd, encodepwd);
       return pwdcheck;
    }
   
   public UserDB create(String email, String id, String pwd, Date birth, String gender) {
      user.setEMAIL(email);
      user.setID(id);
      user.setPWD(encodePWD(pwd));
      user.setBIRTH(birth);
      user.setGENDER(gender);
      

      System.out.println("----repository----");
      System.out.println(user.getEMAIL());
      System.out.println(user.getPWD());
      System.out.println(user.getBIRTH());
      System.out.println(user.getGENDER());
      
      
      this.userRepository.save(user);   
      return user;
   }
   
   //이메일 아이디 중복확인 메서드
   public boolean existIdOrEmail(String id) {
      boolean ck;
      if (id.contains("@")) {
         ck = this.userRepository.existsByEMAIL(id);
      } else {
         ck = this.userRepository.existsByID(id);
      }
      return ck;
   }
   
   //유저 정보가 디비와 일치하는 지 확인하는 메서드
   public boolean existUser(String id, String pwd) {
      boolean ck,ck2;
      if (id.contains("@")) {
         ck = this.userRepository.existsByEMAIL(id);
         user = this.userRepository.findByEMAIL(id);//pwd ck 위해서 유저 받아옴
      } else {
         ck = this.userRepository.existsByID(id);
         user = this.userRepository.findByID(id);//pwd ck 위해서 유저 받아옴
      }
      
      if (ck == true) {
         ck2 = PWDMatches(pwd);
      } else {
         ck2 = false;
      }
      
      user = null;
      
      return ck2;
   }
   //암호화는 한번만 실행되어야 함 (반복 호출 시 암호화 값이 달라져서 비밀번호가 달라짐. 그래서 어떤 방법으로도 DB와 일치시킬 수 없음)
}