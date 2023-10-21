package com.yuhan.loco.prefer;

import java.util.List;

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
	
	public List<PreferDB> getprefer(){
		return preferRepository.findAll();
	}
	
	public void updateGenre(PreferDTO preferDTO, PreferDB preferdb) {
		preferdb.setLove(preferDTO.getUserLike());
		//System.out.println(preferDTO.getUserLike());
		this.preferRepository.save(preferdb);
	}
	
	public void updateGenreHate(PreferDTO preferDTO, PreferDB preferdb) {
		preferdb.setDislike(preferDTO.getUserHate());
		//System.out.println(preferDTO.getUserHate());
		this.preferRepository.save(preferdb);
	}
}
