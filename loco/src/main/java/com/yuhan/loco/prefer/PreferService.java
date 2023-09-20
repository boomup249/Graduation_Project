package com.yuhan.loco.prefer;

import org.springframework.stereotype.Service;


@Service
public class PreferService {
	private final PreferRepository preferRepository;
	
	public PreferService(PreferRepository preferRepository) {
        this.preferRepository = preferRepository;
    }
	
	public PreferDB create(String id, String like, String hate) {
		PreferDB user = new PreferDB();
		user.setID(id);
		
		//장르
		String likeGenre[] = like.split(","); //like
		String hateGenre[] = hate.split(","); //hate
		
		int l_len = likeGenre.length;
		int h_len = hateGenre.length;
		
		//like
		for(int i = 0; i < l_len; i++) {
			switch (likeGenre[i]) {
			case "action":
				user.setAction("true");
				break;
				
			case "shooting":
				user.setShooting("true");
				break;
				
			case "adventure":
				user.setAdventure("true");
				break;
				
			case "fighting":
				user.setFighting("true");
				break;
				
			case "roguelike":
				user.setRoguelike("true");
				break;
				
			case "RPG":
				user.setRPG("true");
				break;
				
			case "MMORPG":
				user.setMMORPG("true");
				break;
				
			case "simulation":
				user.setSimulation("true");
				break;
				
			case "sports":
				user.setSports("true");
				break;
			case "puzzle":
				user.setPuzzle("true");
				break;
				
			case "arcade":
				user.setArcade("true");
				break;
				
			case "strat":
				user.setStrat("true");
				break;
				
			case "horror":
				user.setHorror("true");
				break;
				
			case "multi":
				user.setMulti("true");
				break;
				
			case "single":
				user.setSingle("true");
				break;
			}
		}
		
		//hate
		for(int i = 0; i < h_len; i++) {
			switch (hateGenre[i]) {
			case "action":
				user.setAction("false");
				break;
				
			case "shooting":
				user.setShooting("false");
				break;
				
			case "adventure":
				user.setAdventure("false");
				break;
				
			case "fighting":
				user.setFighting("false");
				break;
				
			case "roguelike":
				user.setRoguelike("false");
				break;
				
			case "RPG":
				user.setRPG("false");
				break;
				
			case "MMORPG":
				user.setMMORPG("false");
				break;
				
			case "simulation":
				user.setSimulation("false");
				break;
				
			case "sports":
				user.setSports("false");
				break;
			case "puzzle":
				user.setPuzzle("false");
				break;
				
			case "arcade":
				user.setArcade("false");
				break;
				
			case "strat":
				user.setStrat("false");
				break;
				
			case "horror":
				user.setHorror("false");
				break;
				
			case "multi":
				user.setMulti("false");
				break;
				
			case "single":
				user.setSingle("false");
				break;
			}
		}
		
		System.out.println("----repository----");
		
		System.out.println(user.getAction());
		System.out.println(user.getShooting());
		System.out.println(user.getAdventure());
		System.out.println(user.getFighting());
		System.out.println(user.getRoguelike());
		System.out.println(user.getRPG());
		System.out.println(user.getMMORPG());
		System.out.println(user.getSimulation());
		System.out.println(user.getSports());
		System.out.println(user.getPuzzle());
		System.out.println(user.getArcade());
		System.out.println(user.getStrat());
		System.out.println(user.getHorror());
		System.out.println(user.getMulti());
		System.out.println(user.getSingle());
		
		this.preferRepository.save(user);
		
		return user;
		
		
	}
	//user찾기

	public PreferDB findUser(String id) { 
	    PreferDB preferdb = this.preferRepository.findByID(id);
	    return preferdb; 
	}
}
