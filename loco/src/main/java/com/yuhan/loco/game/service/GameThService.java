package com.yuhan.loco.game.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yuhan.loco.game.ConsoleDB;
import com.yuhan.loco.game.ConsoleRepository;
import com.yuhan.loco.game.GameDTO;
import com.yuhan.loco.game.PcDB;
import com.yuhan.loco.game.PcRepository;
import com.yuhan.loco.game.genre.ConsoleGenreRepository;
import com.yuhan.loco.game.genre.ConsoleTagRepository;
import com.yuhan.loco.game.genre.PcGenreRepository;
import com.yuhan.loco.game.genre.PcTagRepository;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;


@Service
public class GameThService {
    private final PcGenreRepository pcGenreRepository;
    private final PcTagRepository pcTagRepository;
    private final ConsoleGenreRepository consoleGenreRepository;
    private final ConsoleTagRepository consoleTagRepository;

    public GameThService(
    		PcGenreRepository pcGenreRepository,
    		PcTagRepository pcTagRepository,
    		ConsoleGenreRepository consoleGenreRepository,
    		ConsoleTagRepository consoleTagRepository) {
        this.pcGenreRepository = pcGenreRepository;
        this.pcTagRepository = pcTagRepository;
        this.consoleGenreRepository = consoleGenreRepository;
        this.consoleTagRepository = consoleTagRepository;
    }

    //test
    public void test1(String title) {
    	List<String> a = pcGenreRepository.findGenresByTitle(title);
    	for (int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i));
		}
    }
    
    //함수
    /* 함수 분류 */

    	//thymeleaf용 함수
    
    //pc 최저가 사이트 찾기 함수
  	public String min_check(String steam_salePrice, String epic_salePrice) {
  		//SITEAVAILABILITY = Both일때 활용
  		//230928기준 steam = '할인 X', epic = 'X'로 표기
  			//230929, 할인율이 없을 시 세일가 탭에 긁어온다고 생각하겠음
  		//크롤링 방법보고 수정해서 쓰기, html 파일에서 thymeleaf로 불러와서 쓰면됨
  			//최저가가 스팀이면 스팀으로 활성화, 최저가가 에픽이면 에픽으로 활성화
  			//pc.html 171번째 줄 주석 <!-- Both --> 부분에 min_check 쓸 부분 표시해 놓음, 활성화만 하면 됨
  		
  		String min = null;
  		int s_price = 0; int e_price = 0;
  		
  		//(원) 붙어 있으면 없애기
  		if(steam_salePrice != null) {
  			steam_salePrice = steam_salePrice.replaceAll("[₩,\\\\s]+", ""); // '\\s' : 공백
  			steam_salePrice = steam_salePrice.strip();
  		}
  		if(epic_salePrice != null) {
  			epic_salePrice = epic_salePrice.replaceAll("[₩,\\\\s]+", "");
  			epic_salePrice = epic_salePrice.strip();
  		}
  		
  		System.out.println("s_s: " + steam_salePrice);
  		System.out.println("e_s: " + epic_salePrice);
  		
  		if(steam_salePrice != null && epic_salePrice != null
  				&& !steam_salePrice.equals("할인X") && !epic_salePrice.equals("X")) //값이 정상인 경우
  		{
  			s_price = Integer.parseInt(steam_salePrice);
  			e_price = Integer.parseInt(epic_salePrice);
  			
  			System.out.println("s_i: " + s_price);
  			System.out.println("e_i: " + e_price);
  			
  			//
  			if(s_price > e_price) {
  				min = "e";
  			} else { //같아도 s
  				min = "s";
  			}
  		} else { //값이 정상이 아닌 경우
  			//둘 다인 경우
  			if ((steam_salePrice == null || steam_salePrice.equals("할인X")) 
  					&& (epic_salePrice == null || epic_salePrice.equals("X"))) {
  				min = "s";//
  			}
  			else if(steam_salePrice == null || steam_salePrice.equals("할인X")) { //스팀값이 이상한 경우
  				min = "e";
  			}
  			else { //에픽값이 이상한 경우
  				min = "s";
  			}
  		}
  		
  		return min;
  	} //min_check end
  	
  	
  	//장르 받아와서 문자열로 이어서 바꿔주는 함수(pc)
  	public String getGenreToString(String title) {
    	List<String> genreList = pcGenreRepository.findGenresByTitle(title);
    	String genres = "";
    	
    	if(genreList.isEmpty()) {//결과가 없다면: 기타
    		
    		genres = "기타";
    		
    	} else {//결과가 있다면
    		
    		for (int i = 0; i < genreList.size(); i++) {
    			
    			if(i == 0) { //첫 칼럼이라면
    				
    				if(i == genreList.size() - 1) {//첫 칼럼이자 마지막 칼럼
        				genres += genreList.get(i);
    				} else {
    					//한칸 띄어쓰기 하지 않고 넣기
        				genres += genreList.get(i) + ",";
    				}
    				
    			}
    			
    			else if(i == genreList.size() - 1) { //마지막 칼럼이라면
    				//',' 쓰지 않기
    				genres += " " + genreList.get(i);
    			}
    			
    			else {//나머지 칼럼
    				//한칸 띄어쓰기 하고 넣기
        			genres += " "+ genreList.get(i) + ",";
    			}

    		}
    		
    	}
    	
    	
    	
    	return genres;
    }//end
  	
  	
  	//장르 받아와서 문자열로 이어서 바꿔주는 함수(console)
  	public String getConsoleGenreToString(String title) {
    	List<String> genreList = consoleGenreRepository.findGenresByTitle(title);
    	String genres = "";
    	
    	if(genreList.isEmpty()) {//결과가 없다면: 기타
    		
    		genres = "기타";
    		
    	} else {//결과가 있다면
    		
    		for (int i = 0; i < genreList.size(); i++) {
    			
    			if(i == 0) { //첫 칼럼이라면
    				
    				if(i == genreList.size() - 1) {//첫 칼럼이자 마지막 칼럼
        				genres += genreList.get(i);
    				} else {
    					//한칸 띄어쓰기 하지 않고 넣기
        				genres += genreList.get(i) + ",";
    				}
    				
    			}
    			
    			else if(i == genreList.size() - 1) { //마지막 칼럼이라면
    				//',' 쓰지 않기
    				genres += " " + genreList.get(i);
    			}
    			
    			else {//나머지 칼럼
    				//한칸 띄어쓰기 하고 넣기
        			genres += " "+ genreList.get(i) + ",";
    			}

    		}
    		
    	}
    	
    	
    	
    	return genres;
    } //end
  	
  	
  	//태그 리스트 받아오는 함수(pc)
  	public List<String> getPcTags(String title){
  		return pcTagRepository.findGenresByTitle(title);
  	}
  	
  	//태그 리스트 받아오는 함수(console)
  	public List<String> getConsoleTags(String title){
  		return consoleTagRepository.findGenresByTitle(title);
  	}

  	
}
