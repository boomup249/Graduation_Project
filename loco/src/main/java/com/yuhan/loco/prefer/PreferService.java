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
		user.setLove(like);
		user.setDislike(hate);
		
		this.preferRepository.save(user);
		
		return user;
		
		
	}
	//user찾기

	public PreferDB findUser(String id) { 
	    PreferDB preferdb = this.preferRepository.findByID(id);
	    return preferdb; 
	}
}
