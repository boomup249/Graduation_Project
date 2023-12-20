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

      if (ck) {
         ck2 = PWDMatches(pwd);
      } else {
         ck2 = false;
      }

      user = new UserDB(); //초기화

      return ck2;
   }

   public UserDB findUser(String id) {
	   //용주 컴퓨터에서는 session에서 getAttribute(user) 실행하면 email을 받아와서 findByEMAIL로 설정
	   //오류나면 findByID로 바꿀것

	   //<2023.10.07>
	   // -> LoginController에서 아이디 or email란에 넣은 값이 session.setAttribute("user", userDTO.getUserId());로 남게 됨
	   	//userDTO.getUserId() 이게 아이디를 가져오는 게 아니라, 그 필드에 있는 값을 무조건 가져오기 때문,
	   //따라서 로그인 세션을 통해 여기 집어넣어서 쓰려면 그냥 이메일인지 아이디인지 판별해서 생성하면 됨, 고쳐 놓겠음
	   UserDB userdb = null;
	   if (id.contains("@")) {
		   userdb = this.userRepository.findByEMAIL(id);
	   } else {
		   userdb = this.userRepository.findByID(id);
	   }

       return userdb;
   }


   //무조건 아이디값 받아오는 함수(이메일 넣든, 아이디 넣든)
   public String findUserId(String id) {
	   //userdb 객체 받아오기
	   UserDB userdb = null;
	   if (id.contains("@")) {
		   userdb = this.userRepository.findByEMAIL(id);
	   } else {
		   userdb = this.userRepository.findByID(id);
	   }

	   //id 받아오기
	   String realId = userdb.getID();
	   return realId;
   }


   //암호화는 한번만 실행되어야 함 (반복 호출 시 암호화 값이 달라져서 비밀번호가 달라짐. 그래서 어떤 방법으로도 DB와 일치시킬 수 없음)
   public void updatePWD(UserDTO userDTO, UserDB userDB) {
	   userDB.setPWD(userDTO.getUserPwd());
	   this.userRepository.save(userDB);
   }
}
