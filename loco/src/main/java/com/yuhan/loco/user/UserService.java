package com.yuhan.loco.user;

import java.sql.Date;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	public UserDB create(String email, String pwd, Date birth, String gender, String like, String hate) {
		UserDB user = new UserDB();
		
		user.setID(email);
		user.setPWD(pwd);
		user.setBIRTH(birth);
		user.setGENDER(gender);
		
		//장르
		String likeGenre[] = like.split(","); //like
		String hateGenre[] = hate.split(","); //hate
		
		int l_len = likeGenre.length;
		int h_len = hateGenre.length;
		
		//like
		for(int i = 0; i < l_len; i++) {
			switch (likeGenre[i]) {
			case "action":
				user.setAction(true);
				break;
				
			case "action adventure":
				user.setAction_adventure(true);
				break;
				
			case "survival":
				user.setSurvival(true);
				break;
				
			case "shooting":
				user.setShooting(true);
				break;
				
			case "FPS":
				user.setFPS(true);
				break;
				
			case "RPG":
				user.setRPG(true);
				break;
				
			case "ARPG":
				user.setARPG(true);
				break;
				
			case "MMORPG":
				user.setMMORPG(true);
				break;
				
			case "open world":
				user.setOpen_world(true);
				break;
				
			case "hack and slash":
				user.setHack_and_slash(true);
				break;
				
			case "adventure":
				user.setAdventure(true);
				break;
				
			case "racing":
				user.setRacing(true);
				break;
				
			case "casual":
				user.setCasual(true);
				break;
				
			case "puzzle":
				user.setPuzzle(true);
				break;
			}
		}
		
		//hate
		for(int i = 0; i < h_len; i++) {
			switch (hateGenre[i]) {
			case "action":
				user.setAction(false);
				break;
				
			case "action adventure":
				user.setAction_adventure(false);
				break;
				
			case "survival":
				user.setSurvival(false);
				break;
				
			case "shooting":
				user.setShooting(false);
				break;
				
			case "FPS":
				user.setFPS(false);
				break;
				
			case "RPG":
				user.setRPG(false);
				break;
				
			case "ARPG":
				user.setARPG(false);
				break;
				
			case "MMORPG":
				user.setMMORPG(false);
				break;
				
			case "open world":
				user.setOpen_world(false);
				break;
				
			case "hack and slash":
				user.setHack_and_slash(false);
				break;
				
			case "adventure":
				user.setAdventure(false);
				break;
				
			case "racing":
				user.setRacing(false);
				break;
				
			case "casual":
				user.setCasual(false);
				break;
				
			case "puzzle":
				user.setPuzzle(false);
				break;
			}
		}
		
		System.out.println("----repository----");
		System.out.println(user.getID());
		System.out.println(user.getPWD());
		System.out.println(user.getBIRTH());
		System.out.println(user.getGENDER());
		
		System.out.println(user.getAction());
		System.out.println(user.getAction_adventure());
		System.out.println(user.getSurvival());
		System.out.println(user.getShooting());
		System.out.println(user.getFPS());
		System.out.println(user.getRPG());
		System.out.println(user.getARPG());
		System.out.println(user.getMMORPG());
		System.out.println(user.getOpen_world());
		System.out.println(user.getHack_and_slash());
		System.out.println(user.getAdventure());
		System.out.println(user.getSports());
		System.out.println(user.getRacing());
		System.out.println(user.getCasual());
		System.out.println(user.getPuzzle());
		
		this.userRepository.save(user);
		
		return user;
	}
}
