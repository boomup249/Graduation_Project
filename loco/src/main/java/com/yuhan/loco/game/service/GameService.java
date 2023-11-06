package com.yuhan.loco.game.service;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import com.yuhan.loco.game.CrawlingTimeDB;
import com.yuhan.loco.game.CrawlingTimeRepository;
import com.yuhan.loco.game.GameDTO;
import com.yuhan.loco.game.PcDB;
import com.yuhan.loco.game.PcRepository;
import com.yuhan.loco.game.genre.ConsoleGenreRepository;
import com.yuhan.loco.game.genre.PcGenreRepository;
import com.yuhan.loco.search.GameSearchDB;
import com.yuhan.loco.search.GameSearchRepository;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;


@Service
public class GameService {
    private final PcRepository pcRepository;
    private final ConsoleRepository consoleRepository;
    private final PcGenreRepository pcGenreRepository;
    private final ConsoleGenreRepository consoleGenreRepository;
    private final CrawlingTimeRepository cTimeRepository;
    private final GameSearchRepository gamesearchRepository;

    public GameService(PcRepository pcRepository,
    		ConsoleRepository consoleRepository,
    		PcGenreRepository pcGenreRepository,
    		ConsoleGenreRepository consoleGenreRepository,
    		CrawlingTimeRepository cTimeRepository,
    		GameSearchRepository gamesearchRepository) {
        this.pcRepository = pcRepository;
        this.consoleRepository = consoleRepository;
        this.pcGenreRepository = pcGenreRepository;
        this.consoleGenreRepository = consoleGenreRepository;
        this.cTimeRepository = cTimeRepository;
        this.gamesearchRepository = gamesearchRepository;
    }
    
    //함수
    /* 함수 분류 */
    
    /*테스트 & 객체 받아오기 함수*/
    
    	//Page<db> or db 객체 받아오기(정렬 x)
    
    //key값으로 해당하는 레코드 db 객체로 받아오기
    public PcDB getPcByKey(String key) {
        return pcRepository.findByKEY(key);
    }

    //num값으로 Console 받아오기
    public ConsoleDB getCsByNum(Integer num) {
	return consoleRepository.findByNUM(num);
    }
    
    //pc 페이지 객체로 findall
    public Page<PcDB> getFullPcList(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return this.pcRepository.findAll(pageable);
    }
	
    //console 페이지 객체로 findall
    public Page<ConsoleDB> getFullConsoleList(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return this.consoleRepository.findAll(pageable);
    }
    
    	//List<db> 객체 받아오기
    public List<PcDB> getAllDataPC() {
        return pcRepository.findAll();
    }

    public List<ConsoleDB> getAllDataConsole() {
        return consoleRepository.findAll();
    }
    
    	//string 받아오기 or 변환
    //크롤링 시간 받아와서 string으로
    //가장 첫번째 칼럼만 참고함
    public String getCrawlingTime() {
    	List<CrawlingTimeDB> cTimeList = cTimeRepository.findAll();
    	String cTime = cTimeList.get(0).getENDTIME().format
    			(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)); //포멧 고칠거면 여기 손보면 됨
    	
    	return cTime;
    }
    
    
    	//테스트
    
    //(테스트용) 사이트 어빌리티 both인 항목 찾기, 쓸거면 형태 고치기(return)
    public void findPcSITEeqBoth() {
        List<PcDB> a = pcRepository.findBySITEAVAILABILITY("Both");
    }
    
    //장르 테이블 함수 작동 테스트
    public List<String> getTitleByGenre(List<String> genre){
    	return pcGenreRepository.findTitlesByGenre(genre);
    }
    
    //크롤링 타임 테스트
    public void testTime() {
    	List<CrawlingTimeDB> a = cTimeRepository.findAll();
    	System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(a.get(0).getENDTIME()));
    	
    	
    }
    /*테스트 & 객체 받아오기 함수 end*/
    

    /*정렬된 Page<db> 객체 받아오기*/
    
    	//pc 정렬 & 필터 적용 Page<db> 객체 만들기 함수
    //
    public Page<PcDB> getPcPageByFilter(int page, String site,
    		String category, String orderby, String like){
    	//한 페이지에 보일 데이터 수
    	int size = 20;
    	
    	//변수 선언
    	List<String> siteList = new ArrayList<>(); //사이트 선택 리스트
    	List<String> titleList = new ArrayList<>(); //카테고리별 타이틀 리스트
    	Pageable pageable = null; //pageable
    	
    	Boolean pick_ck = false; //정렬에서 추천순을 선택했는지 여부
    	
    	//site
    	switch (site) {
    	//0 & all: 미선택시 all 적용
		case "0": case "all":
			siteList.add("Both");
			siteList.add("Steam Only");
			siteList.add("Epic Only");
			
			break;
			
		case "steam":
			siteList.add("Both");
			siteList.add("Steam Only");
			
			break;
			
		case "epic":
			siteList.add("Both");
			siteList.add("Epic Only");
			
			break;

		default:
			break;
		} //site end
    	
    	
    	//orderby
    	switch (orderby) {
    	//정렬 기준 & 사이트에 따라 pageable 만들기
		case "popular":
			
			//site에 따라 만들기
	    	switch (site) {
	    	//0 & all: 미선택시 all 적용
			case "0": case "all":
				pageable = PageRequest.of(page, size); //크롤링할때 정렬해서 가지고 옴
				break;
				
			case "steam":
				pageable = PageRequest.of(page, size, Sort.by("STEAMNUM").ascending());
				break;
				
			case "epic":
				pageable = PageRequest.of(page, size, Sort.by("EPICNUM").ascending());
				break;

			default:
				break;
			} //site end
			
			break;
			
		case "sale":
			
			//site에 따라 만들기
	    	switch (site) {
	    	//0 & all: 미선택시 all 적용
			case "0": case "all":
				pageable = PageRequest.of(page, size, Sort.by("SALEPER").descending());
				break;
				
			case "steam":
				pageable = PageRequest.of(page, size, Sort.by("STEAMSALEPER").descending());
				break;
				
			case "epic":
				pageable = PageRequest.of(page, size, Sort.by("EPICSALEPER").descending());
				break;

			default:
				break;
			} //site end
			
			break;
			
		case "pick":
			
			pick_ck = true; //pick 선택
			
				//pageable
			//site에 따라 만들기
	    	switch (site) {
	    	//0 & all: 미선택시 all 적용
			case "0": case "all":
				pageable = PageRequest.of(page, size);
				break;
				
			case "steam":
				pageable = PageRequest.of(page, size, Sort.by("STEAMNUM").ascending());
				break;
				
			case "epic":
				pageable = PageRequest.of(page, size, Sort.by("EPICNUM").ascending());
				break;

			default:
				break;
			} //site end
			
	    		//likeList
			//로그인한 유저, 선호 정보 가져오기
	    	List<String> likeList = new ArrayList<>();
	    	
	    	
	    	//선호 장르 문자열에 따라 가져올 카테고리 리스트(likeList) 만들기
	    	if(like == null) {//선호 장르가 null인 경우 제외
	    		likeList = null;
	    	} else { // ',' 없이 값이 하나만 있어도 하나만 array 생성됨
	    		String[] likeArray = like.split(",");
		    	
		    	for (int i = 0; i < likeArray.length; i++) { //선호하는 카테고리 리스트 만들기
		    		
					switch (likeArray[i]) {
					//장르 한글 변환
					case "action":
						likeList.add("액션");
						break;
						
					case "shooting":
						likeList.add("슈팅");
						break;
						
					case "adventure":
						likeList.add("어드벤처");
						break;
						
					case "fighting":
						likeList.add("격투");
						break;
						
					case "roguelike":
						likeList.add("로그라이크");
						break;
						
					case "RPG":
						likeList.add("RPG");
						break;
						
					case "mmorpg":
						likeList.add("MMORPG");
						break;
						
					case "simulation":
						likeList.add("시뮬레이션");
						break;
						
					case "sports":
						likeList.add("스포츠");
						break;
						
					case "puzzle":
						likeList.add("퍼즐");
						break;
						
					case "arcade":
						likeList.add("아케이드");
						break;
						
					case "strat":
						likeList.add("전략");
						break;
						
					case "horror":
						likeList.add("공포");
						break;
						
					case "multi":
						likeList.add("멀티플레이어");
						break;
						
					case "single":
						likeList.add("싱글 플레이어");
						break;

					default:
						break;
					} //switch end
					
				}//for end
	    		
	    	} //if else end
	    	
	    	
	    	//
	    	//선호 리스트에 따라 타이틀 받아오기
        	if(likeList == null) { //데이터 0개
        		titleList = null;
        	} else {
        		List<String> getTitle = pcGenreRepository.findTitlesByGenre(likeList);
        		titleList.addAll(getTitle);
        	}
	    	
	    	//pick은 like 리스트로 따로 페이지 가져오기
			
			break;

		default:
			break;
		} //orderby end
    	
    	
    	//결과
    	
    	//추천순인지 아닌지
    	if(pick_ck == true) {
    		//추천순이면 카테고리 선택을 보지 않고(애초에 html에서 비활성화), 유저가 선호하는 장르를 가져옴
    		//선호하는 장르를 모두 select해서 (모두 or 30개) 중 min 개수로 화면에 보여주도록 함
    		
    		if(titleList != null) { //보여줄 목록이 있는 경우
    			int limit = 30; //추천으로 보여줄 개수
        		
        		Page<PcDB> allPage = pcRepository.findByCriteria(siteList, titleList, pageable);
        		List<PcDB> subList = allPage.getContent().subList(0, Math.min(allPage.getContent().size(), limit));
        		
        		Page<PcDB> orgPage = new PageImpl<>(subList, pageable, subList.size());
        		
        		return orgPage;
    		} else { //선호 장르가 없어 보여줄 목록이 없는 경우
    			List<PcDB> emptyList = Collections.emptyList();
    			pageable = PageRequest.of(0, 10);
    			Page<PcDB> emptyPage = new PageImpl<>(emptyList, pageable, 0);
    			
    			return emptyPage;
    		}
    		
    		
    		
    	} else {
    		//추천순이 아니면 카테고리 활성화
    		//category: 단일 선택으로 막아놔서 switch case 가능
        	List<String> categoryList = new ArrayList<>();
        	
        	switch (category) {
        	//0 & all: 미선택시 all 적용
    		case "0": case "all":
    			categoryList = null;
    			break;
    		//각 장르에 따라 titles 받아옴
    		case "action":
    			categoryList.add("액션");
    			break;
    			
    		case "shooting":
    			categoryList.add("슈팅");
    			break;
    			
    		case "adventure":
    			categoryList.add("어드벤처");
    			break;
    			
    		case "fighting":
    			categoryList.add("격투");
    			break;
    			
    		case "roguelike":
    			categoryList.add("로그라이크");
    			break;
    			
    		case "RPG":
    			categoryList.add("RPG");
    			break;
    			
    		case "mmorpg":
    			categoryList.add("MMORPG");
    			break;
    			
    		case "simulation":
    			categoryList.add("시뮬레이션");
    			break;
    			
    		case "sports":
    			categoryList.add("스포츠");
    			break;
    			
    		case "puzzle":
    			categoryList.add("퍼즐");
    			break;
    			
    		case "arcade":
    			categoryList.add("아케이드");
    			break;
    			
    		case "strat":
    			categoryList.add("전략");
    			break;
    			
    		case "horror":
    			categoryList.add("공포");
    			break;
    			
    		case "multi":
    			categoryList.add("멀티플레이어");
    			break;
    			
    		case "single":
    			categoryList.add("싱글 플레이어");
    			break;

    		default:
    			break;
    		} //switch end
        	
        	//카테고리 리스트에 따라 타이틀 받아오기
        	if(categoryList == null) { //전체 선택
        		//title List를 아예 안 받는 함수에 넣어서 전체 데려오기
        		Page<PcDB> normalPage = pcRepository.findByCriteriaOnlySite(siteList, pageable);
        		return normalPage;
        	} else {
        		//title List 사용 함수
        		List<String> getTitle = pcGenreRepository.findTitlesByGenre(categoryList);
        		titleList.addAll(getTitle);
        		Page<PcDB> normalPage = pcRepository.findByCriteria(siteList, titleList, pageable);
        		return normalPage;
        	}
        	//category end
        	
		}
    	//결과 end
    	
    }//getPcPageByFilter 함수 end
    
    
    //test
    public Page<PcDB> testpcgr(int page){
    	int size = 20;
    	List<String> siteList = Arrays.asList("Steam Only", "Both");
    	List<String> gList = Arrays.asList("액션", "슈팅");
    	//List<String> titleList = pcGenreRepository.findTitlesByGenre(gList);
    	//List<String> titleList = Arrays.asList("Muse Dash", "Muse Dash - Just as planned");
    	List<String> titleList = null;
    	Pageable pageable = PageRequest.of(page, size);
    	
    	return pcRepository.findByCriteria(siteList, titleList, pageable);
    }
    
    
    
    
    	//console 정렬 & 필터 적용 Page<db> 객체 만들기 함수
    //
    public Page<ConsoleDB> getConsolePageByFilter(int page, String site,
    		String category, String orderby, String like){
    	//한 페이지에 보일 데이터 수
    	int size = 20;
    	
    	//변수 선언
    	List<String> siteList = new ArrayList<>(); //사이트 선택 리스트
    	List<String> titleList = new ArrayList<>(); //카테고리별 타이틀 리스트
    	Pageable pageable = null; //pageable
    	
    	Boolean pick_ck = false; //정렬에서 추천순을 선택했는지 여부
    	
    	//site
    	switch (site) {
    	//0 & all: 미선택시 all 적용
		case "0": case "all":
			siteList.add("Ps Only");
			siteList.add("Switch Only");
			
			break;
			
		case "ps":
			siteList.add("Ps Only");
			
			break;
			
		case "nintendo":
			siteList.add("Switch Only");
			
			break;

		default:
			break;
		} //site end
    	
    	
    	//orderby
    	switch (orderby) {
    	//정렬 기준 & 사이트에 따라 pageable 만들기
		case "popular":
			
			//site에 따라 만들기
	    	switch (site) {
	    	//0 & all: 미선택시 all 적용
			case "0": case "all":
				pageable = PageRequest.of(page, size); //크롤링할때 정렬해서 가지고 옴 //현재 플스가 위로 감
				break;
				
			case "ps": case "nintendo":
				pageable = PageRequest.of(page, size, Sort.by("NUM").ascending());
				break;

			default:
				break;
			} //site end
			
			break;
			
		case "sale":
			
			//site에 따라 만들기
	    	switch (site) {
	    	//0 & all: 미선택시 all 적용
			case "0": case "all": case "ps": case "nintendo":
				pageable = PageRequest.of(page, size, Sort.by("SALEPER").descending());
				break;

			default:
				break;
			} //site end
			
			break;
			
		case "pick":
			
			pick_ck = true; //pick 선택
			
				//pageable
			//site에 따라 만들기
	    	switch (site) {
	    	//0 & all: 미선택시 all 적용
			case "0": case "all":
				pageable = PageRequest.of(page, size);
				break;
				
			case "ps": case "nintendo":
				pageable = PageRequest.of(page, size, Sort.by("NUM").ascending());
				break;
				
			default:
				break;
			} //site end
			
	    		//likeList
			//로그인한 유저, 선호 정보 가져오기
	    	List<String> likeList = new ArrayList<>();
	    	
	    	
	    	//선호 장르 문자열에 따라 가져올 카테고리 리스트(likeList) 만들기
	    	if(like == null) {//선호 장르가 null인 경우 제외
	    		likeList = null;
	    	} else { // ',' 없이 값이 하나만 있어도 하나만 array 생성됨
	    		String[] likeArray = like.split(",");
		    	
		    	for (int i = 0; i < likeArray.length; i++) { //선호하는 카테고리 리스트 만들기
		    		
					switch (likeArray[i]) {
					//장르 한글 변환
					case "action":
						likeList.add("액션");
						break;
						
					case "shooting":
						likeList.add("슈팅");
						break;
						
					case "adventure":
						likeList.add("어드벤처");
						break;
						
					case "fighting":
						likeList.add("격투");
						break;
						
					case "roguelike":
						likeList.add("로그라이크");
						break;
						
					case "RPG":
						likeList.add("RPG");
						break;
						
					case "mmorpg":
						likeList.add("MMORPG");
						break;
						
					case "simulation":
						likeList.add("시뮬레이션");
						break;
						
					case "sports":
						likeList.add("스포츠");
						break;
						
					case "puzzle":
						likeList.add("퍼즐");
						break;
						
					case "arcade":
						likeList.add("아케이드");
						break;
						
					case "strat":
						likeList.add("전략");
						break;
						
					case "horror":
						likeList.add("공포");
						break;
						
					case "multi":
						likeList.add("멀티플레이어");
						break;
						
					case "single":
						likeList.add("싱글 플레이어");
						break;

					default:
						break;
					} //switch end
					
				}//for end
	    		
	    	} //if else end
	    	
	    	
	    	//
	    	//선호 리스트에 따라 타이틀 받아오기
        	if(likeList == null) { //데이터 0개
        		titleList = null;
        	} else {
        		List<String> getTitle = consoleGenreRepository.findTitlesByGenre(likeList);
        		titleList.addAll(getTitle);
        	}
	    	
	    	//pick은 like 리스트로 따로 페이지 가져오기
			
			break;

		default:
			break;
		} //orderby end
    	
    	
    	//결과
    	
    	//추천순인지 아닌지
    	if(pick_ck == true) {
    		//추천순이면 카테고리 선택을 보지 않고(애초에 html에서 비활성화), 유저가 선호하는 장르를 가져옴
    		//선호하는 장르를 모두 select해서 (모두 or 30개) 중 min 개수로 화면에 보여주도록 함
    		
    		if(titleList != null) { //보여줄 목록이 있는 경우
    			int limit = 30; //추천으로 보여줄 개수
        		
        		Page<ConsoleDB> allPage = consoleRepository.findByCriteria(siteList, titleList, pageable);
        		List<ConsoleDB> subList = allPage.getContent().subList(0, Math.min(allPage.getContent().size(), limit));
        		
        		Page<ConsoleDB> orgPage = new PageImpl<>(subList, pageable, subList.size());
        		
        		return orgPage;
    		} else { //선호 장르가 없어 보여줄 목록이 없는 경우
    			List<ConsoleDB> emptyList = Collections.emptyList();
    			pageable = PageRequest.of(0, 10);
    			Page<ConsoleDB> emptyPage = new PageImpl<>(emptyList, pageable, 0);
    			
    			return emptyPage;
    		}
    		
    		
    		
    	} else {
    		//추천순이 아니면 카테고리 활성화
    		//category: 단일 선택으로 막아놔서 switch case 가능
        	List<String> categoryList = new ArrayList<>();
        	
        	switch (category) {
        	//0 & all: 미선택시 all 적용
    		case "0": case "all":
    			categoryList = null;
    			break;
    		//각 장르에 따라 titles 받아옴
    		case "action":
    			categoryList.add("액션");
    			break;
    			
    		case "shooting":
    			categoryList.add("슈팅");
    			break;
    			
    		case "adventure":
    			categoryList.add("어드벤처");
    			break;
    			
    		case "fighting":
    			categoryList.add("격투");
    			break;
    			
    		case "roguelike":
    			categoryList.add("로그라이크");
    			break;
    			
    		case "RPG":
    			categoryList.add("RPG");
    			break;
    			
    		case "mmorpg":
    			categoryList.add("MMORPG");
    			break;
    			
    		case "simulation":
    			categoryList.add("시뮬레이션");
    			break;
    			
    		case "sports":
    			categoryList.add("스포츠");
    			break;
    			
    		case "puzzle":
    			categoryList.add("퍼즐");
    			break;
    			
    		case "arcade":
    			categoryList.add("아케이드");
    			break;
    			
    		case "strat":
    			categoryList.add("전략");
    			break;
    			
    		case "horror":
    			categoryList.add("공포");
    			break;
    			
    		case "multi":
    			categoryList.add("멀티플레이어");
    			break;
    			
    		case "single":
    			categoryList.add("싱글 플레이어");
    			break;

    		default:
    			break;
    		} //switch end
        	
        	//카테고리 리스트에 따라 타이틀 받아오기
        	if(categoryList == null) { //전체 선택
        		//title List를 아예 안 받는 함수에 넣어서 전체 데려오기
        		Page<ConsoleDB> normalPage = consoleRepository.findByCriteriaOnlySite(siteList, pageable);
        		return normalPage;
        	} else {
        		//title List 사용 함수
        		List<String> getTitle = consoleGenreRepository.findTitlesByGenre(categoryList);
        		titleList.addAll(getTitle);
        		Page<ConsoleDB> normalPage = consoleRepository.findByCriteria(siteList, titleList, pageable);
        		return normalPage;
        	}
        	//category end
        	
		}
    	//결과 end
    	
    }//getConsolePageByFilter 함수 end
    
    
    /*정렬된 Page<db> 객체 받아오기 end*/
    
  	
  	/*dto 변환 or dto 받아오기*/
    
  		//dto 방식 사용때 활용
    //
    public List<GameDTO> getAllGames() {
        List<PcDB> gameDBList = pcRepository.findAll();
        List<GameDTO> gameDTOList = gameDBList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return gameDTOList;
    }

    /*
    public List<ConsoleDTO> getAllGames_c() {
        List<ConsoleDB> gameDBList = consoleRepository.findAll();
        List<ConsoleDTO> gameDTOList = gameDBList.stream()
                .map(this::convertToDTO_c)
                .collect(Collectors.toList());

        return gameDTOList;
    }
    */
    
    private GameDTO convertToDTO(PcDB pcDB) {
        GameDTO gameDTO = new GameDTO();
        if (pcDB != null) {
        	String site = pcDB.getSITEAVAILABILITY();
            String price1 = pcDB.getSTEAMPRICE();
            String price2 = pcDB.getEPICPRICE();
            int price1D =0 ;
            int price2D =0 ;
            
            if (price1 != null && price1.equals("무료")) {
                gameDTO.setPRICE("무료");
            }
            else if(price1 != null) {
                price1 = price1.replaceAll("[₩,\\s]+", "");
                price1D = Integer.parseInt(price1);
            }
            else {
            	gameDTO.setPRICE("X");
            }
            if (price2 != null && price2.equals("무료")) {
            	gameDTO.setPRICE("무료");
            }
            else if(price2 != null && price2.equals("무료")) {
            	price2 = price2.replaceAll("[₩,\\s]+", "");
            	price2D = Integer.parseInt(price2);
            }
            else {
            	gameDTO.setPRICE("X");
            }

            if(site.equals("Steam Only")) {
                gameDTO.setTITLE(pcDB.getTITLE());
                gameDTO.setPRICE(pcDB.getSTEAMPRICE());
                gameDTO.setSALEPRICE(pcDB.getSTEAMSALEPRICE());
                gameDTO.setSALEPER(pcDB.getSTEAMSALEPER());
                gameDTO.setDESCRIPTION(pcDB.getSTEAMDESCRIPTION());
                gameDTO.setIMGDATA(pcDB.getSTEAMIMGDATA());
                gameDTO.setGAMEIMG(pcDB.getSTEAMGAMEIMG());
                gameDTO.setURL(pcDB.getSTEAMURL());
            	
            }
            else if(site.equals("Epic Only")) {		
                gameDTO.setTITLE(pcDB.getTITLE());
                gameDTO.setPRICE(pcDB.getEPICPRICE());
                gameDTO.setSALEPRICE(pcDB.getEPICSALEPRICE());
                gameDTO.setSALEPER(pcDB.getEPICSALEPER());
                gameDTO.setDESCRIPTION(pcDB.getEPICDESCRIPTION());
                gameDTO.setIMGDATA(pcDB.getEPICIMGDATA());
                gameDTO.setGAMEIMG(pcDB.getEPICGAMEIMG());
                gameDTO.setURL(pcDB.getEPICURL());
            }
            else {
            	if(price1D > price2D || price1 == null) {
                    gameDTO.setTITLE(pcDB.getTITLE());
                    gameDTO.setPRICE(pcDB.getEPICPRICE());
                    gameDTO.setSALEPRICE(pcDB.getEPICSALEPRICE());
                    gameDTO.setSALEPER(pcDB.getEPICSALEPER());
                    gameDTO.setDESCRIPTION(pcDB.getEPICDESCRIPTION());
                    gameDTO.setIMGDATA(pcDB.getEPICIMGDATA());
                    gameDTO.setGAMEIMG(pcDB.getEPICGAMEIMG());
                    gameDTO.setURL(pcDB.getEPICURL());
            	}
            	else {
                    gameDTO.setTITLE(pcDB.getTITLE());
                    gameDTO.setPRICE(pcDB.getEPICPRICE());
                    gameDTO.setSALEPRICE(pcDB.getSTEAMSALEPRICE());
                    gameDTO.setSALEPER(pcDB.getSTEAMSALEPER());
                    gameDTO.setDESCRIPTION(pcDB.getSTEAMDESCRIPTION());
                    gameDTO.setIMGDATA(pcDB.getSTEAMIMGDATA());
                    gameDTO.setGAMEIMG(pcDB.getSTEAMGAMEIMG());
                    gameDTO.setURL(pcDB.getSTEAMURL());
            	}
            }
            
        }
        return gameDTO;
    }
    
/*
    private ConsoleDTO convertToDTO_c(ConsoleDB consoleDB) {
        ConsoleDTO consoleDTO = new ConsoleDTO();
        if(consoleDB != null) {
        	String site = consoleDB.getSITEAVAILABILITY();
        	String price1 = consoleDB.getPSPRICE();
        	int price1D =0;
        	int price2D =0;
        	if(price1 != null) {
        		price1 = price1.replaceAll("[원,\\s]+", "");
        		price1D = Integer.parseInt(price1);
        	}
        	String price2 = consoleDB.getSWITCHPRICE();
        	if(price2 != null) {
        		price2 = price2.replaceAll("[₩,\\s]+", "");
        		price2D = Integer.parseInt(price2);
        	}
        	
        	
        	if(site.equals("PS Only")) {
        		consoleDTO.setPRICE(consoleDB.getPSPRICE());
            	consoleDTO.setTITLE(consoleDB.getTITLE());
            	consoleDTO.setSALEPER(consoleDB.getPSSALEPER());
            	consoleDTO.setSALEPRICE(consoleDB.getPSSALEPRICE());
            	consoleDTO.setDESCRIPTION(consoleDB.getPSDESCRIPTION());
            	consoleDTO.setIMGDATA(consoleDB.getPSIMGDATA());
            	consoleDTO.setGAMEIMG(consoleDB.getPSGAMEIMG());
            	consoleDTO.setURL(consoleDB.getPSURL());
        	}
        	else if(site.equals("Switch Only")) {
        		consoleDTO.setPRICE(consoleDB.getSWITCHPRICE());
            	consoleDTO.setTITLE(consoleDB.getTITLE());
            	consoleDTO.setSALEPER(consoleDB.getSWITCHSALEPER());
            	consoleDTO.setSALEPRICE(consoleDB.getSWITCHSALEPRICE());
            	consoleDTO.setDESCRIPTION(consoleDB.getSWITCHDESCRIPTION());
            	consoleDTO.setIMGDATA(consoleDB.getSWITCHIMGDATA());
            	consoleDTO.setGAMEIMG(consoleDB.getSWITCHGAMEIMG());
            	consoleDTO.setURL(consoleDB.getSWITCHURL());
        	}
        	else {
        		if(price1D > price2D) {
            		consoleDTO.setPRICE(consoleDB.getSWITCHPRICE());
                	consoleDTO.setTITLE(consoleDB.getTITLE());
                	consoleDTO.setSALEPER(consoleDB.getSWITCHSALEPER());
                	consoleDTO.setSALEPRICE(consoleDB.getSWITCHSALEPRICE());
                	consoleDTO.setDESCRIPTION(consoleDB.getSWITCHDESCRIPTION());
                	consoleDTO.setIMGDATA(consoleDB.getSWITCHIMGDATA());
                	consoleDTO.setGAMEIMG(consoleDB.getSWITCHGAMEIMG());
                	consoleDTO.setURL(consoleDB.getSWITCHURL());
        		}
        		else {
            		consoleDTO.setPRICE(consoleDB.getPSPRICE());
                	consoleDTO.setTITLE(consoleDB.getTITLE());
                	consoleDTO.setSALEPER(consoleDB.getPSSALEPER());
                	consoleDTO.setSALEPRICE(consoleDB.getPSSALEPRICE());
                	consoleDTO.setDESCRIPTION(consoleDB.getPSDESCRIPTION());
                	consoleDTO.setIMGDATA(consoleDB.getPSIMGDATA());
                	consoleDTO.setGAMEIMG(consoleDB.getPSGAMEIMG());
                	consoleDTO.setURL(consoleDB.getPSURL());
        		}
        	}
        }

        return consoleDTO;
    }
    */
    
    
    public GameDTO createToDTO(String key, PcDB pcDB) {
        GameDTO gameDTO = new GameDTO();
	//게임 판별
        if (key != null) {
            PcDB selectedPcGame = pcRepository.findByKEY(key);
            if (selectedPcGame != null) {

             	// TITLE 설정
                gameDTO.setTITLE(selectedPcGame.getTITLE());
		// STEAM or EPIC 구분
                gameDTO.setSITEAVAILABILITY(pcDB.getSITEAVAILABILITY());
     
                if ("Steam Only".equalsIgnoreCase(pcDB.getSITEAVAILABILITY())) {
                    gameDTO.setIMGDATA(pcDB.getSTEAMIMGDATA());
                    gameDTO.setGAMEIMG(pcDB.getSTEAMGAMEIMG());
                    gameDTO.setDESCRIPTION(pcDB.getSTEAMDESCRIPTION());
                    gameDTO.setURL(pcDB.getSTEAMURL());
                }
                else if("Epic Only".equalsIgnoreCase(pcDB.getSITEAVAILABILITY())) {
                    gameDTO.setIMGDATA(pcDB.getEPICIMGDATA());
                    gameDTO.setGAMEIMG(pcDB.getEPICGAMEIMG());
                    gameDTO.setDESCRIPTION(pcDB.getEPICDESCRIPTION());
                    gameDTO.setURL(pcDB.getEPICURL());
                }
                else if("Both".equalsIgnoreCase(pcDB.getSITEAVAILABILITY())) {
                    gameDTO.setIMGDATA(pcDB.getSTEAMIMGDATA());
                    gameDTO.setGAMEIMG(pcDB.getSTEAMGAMEIMG());
                    gameDTO.setDESCRIPTION(pcDB.getSTEAMDESCRIPTION());
                }             
                return gameDTO;
            }
        }
        return gameDTO;
    }
    
    public Page<GameSearchDB> getSearchPageByFilter(String searchKeyword, int page){
    	//한 페이지에 보일 데이터 수
    	int size = 20;
    	Pageable pageable = PageRequest.of(page, size);
    	Page<GameSearchDB> normalPage = gamesearchRepository.findByTITLE(searchKeyword, pageable);
        return normalPage;
		}
    
}
