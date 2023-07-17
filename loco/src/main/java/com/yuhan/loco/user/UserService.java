package com.yuhan.loco.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	public UserDB create(String email, String pwd) {
		UserDB user = new UserDB();
		
		user.setID(email);
		user.setPWD(pwd);
		user.setBIRTH(null);
		user.setGENDER(null);
		
		System.out.println("----repository----");
		System.out.println(user.getID());
		System.out.println(user.getPWD());
		
		this.userRepository.save(user);
		
		return user;
	}
}
