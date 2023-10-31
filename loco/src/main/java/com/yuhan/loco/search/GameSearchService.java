package com.yuhan.loco.search;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GameSearchService {
	private GameSearchRepository gamesearchRepository;
	
	public GameSearchService(GameSearchRepository gamesearchRepository) {
		this.gamesearchRepository = gamesearchRepository;
	}
	
	public List<GameSearchDB> SearchList(String search_str){
		List<GameSearchDB> result = gamesearchRepository.findByTITLEContaining(search_str);
		return result;
	}
}
