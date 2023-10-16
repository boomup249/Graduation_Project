package com.yuhan.loco.news;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class NewsService {

	//
	private final NewsRepository nRepository;
	
	public NewsService(NewsRepository nRepository) {
		this.nRepository = nRepository;
	}
	
	//
		//etc -> null인 데이터(달력에 넣을 데이터 뽑아오기)
	public List<NewsDTO> getCalendarNews(){
		List<NewsDB> nDBList = nRepository.findEtcIsNull(); //달력에 넣을 데이터 리스트
		
		List<NewsDTO> NewsDTOList = new ArrayList<>(); //dto 담을 리스트
		
		for (int i = 0; i < nDBList.size(); i++) { //다 담기
			NewsDTO nDTO = new NewsDTO();
			
			nDTO.setStart(nDBList.get(i).getDATE());
			nDTO.setTitle(nDBList.get(i).getTITLE());
			nDTO.setPlatform(nDBList.get(i).getPLATFORM());
			nDTO.setPrice(nDBList.get(i).getPRICE());
			nDTO.setEtc(nDBList.get(i).getETC());
			
			NewsDTOList.add(nDTO);
		}
		
		return NewsDTOList;
	}
	
	// etc -> not null인 데이터
	public List<NewsDTO> getMemoNews(){
		List<NewsDB> nDBList = nRepository.findEtcIsNotNull(); //달력에 넣을 데이터 리스트
		
		List<NewsDTO> NewsDTOList = new ArrayList<>(); //dto 담을 리스트
		
		for (int i = 0; i < nDBList.size(); i++) { //다 담기
			NewsDTO nDTO = new NewsDTO();
		
			nDTO.setTitle(nDBList.get(i).getTITLE());
			nDTO.setPlatform(nDBList.get(i).getPLATFORM());
			nDTO.setPrice(nDBList.get(i).getPRICE());
			nDTO.setEtc(nDBList.get(i).getETC());
			
			NewsDTOList.add(nDTO);
		}
		
		return NewsDTOList;
	}
}
