package com.yuhan.loco.game;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yuhan.loco.game.genre.PcGenreRepository;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;


@Service
public class GameService {
    private final PcRepository pcRepository;
    private final ConsoleRepository consoleRepository;
    private final PcGenreRepository pcGenreRepository;

    public GameService(PcRepository pcRepository,
    		ConsoleRepository consoleRepository,
    		PcGenreRepository pcGenreRepository) {
        this.pcRepository = pcRepository;
        this.consoleRepository = consoleRepository;
        this.pcGenreRepository = pcGenreRepository;
    }
    
    //(테스트용) 사이트 어빌리티 both인 항목 찾기, 쓸거면 형태 고치기(return)
    public void findPcSITEeqBoth() {
        List<PcDB> a = pcRepository.findBySITEAVAILABILITY("Both");
    }
    
    /*
    //장르 테이블 연결 테스트
    public void test() {
    	System.out.println((pcGenreRepository.findByNUM(9)).getTITLE());
    }*/
    
    //장르 테이블 함수 작동 테스트
    public List<String> getTitleByGenre(List<String> genre){
    	return pcGenreRepository.findTitlesByGenre(genre);
    }

    /*
    public void findConsoleSITE() {
        List<ConsoleDB> a = consoleRepository.findBySITEAVAILABILITY("Both");
        System.out.println(a);
    }
    */
    
    //key값으로 해당하는 레코드 db 객체로 받아오기
    public PcDB getPcByKey(String key) {
        return pcRepository.findByKEY(key);
    }

    //num값으로 Console 받아오기
    public ConsoleDB getCsByNum(Integer num) {
	return consoleRepository.findByNUM(num);
    }
    
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
  			steam_salePrice = steam_salePrice.replaceAll("[₩,\\s]+", "");
  		}
  		if(epic_salePrice != null) {
  			epic_salePrice = epic_salePrice.replaceAll("[₩,\\s]+", "");
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

    public List<PcDB> getAllDataPC() {
        return pcRepository.findAll();
    }

    public List<ConsoleDB> getAllDataConsole() {
        return consoleRepository.findAll();
    }

    
    //
		//dto 방식 사용때 활용
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
    
    	//dto 방식 사용때 활용 end
    //
    
    
}
