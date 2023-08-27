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
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

	
	public UserDB create(String email, String id, String pwd, Date birth, String gender) {
		UserDB user = new UserDB();
		String encodePWD = passwordEncoder.encode(pwd); //비밀번호 암호화 부분
		user.setEMAIL(email);
		user.setID(id);
		user.setPWD(encodePWD);
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
		boolean ck;
		String encodePWD = passwordEncoder.encode(pwd);
		if (id.contains("@")) {
			ck = this.userRepository.existsByEMAILAndPWD(id, encodePWD);
		} else {
			ck = this.userRepository.existsByIDAndPWD(id, encodePWD);
		}
		
		return ck;
	}
	
}
