package com.yuhan.loco.game;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class GameService {
	//전역
    private final PcRepository pcRepository;
    private final ConsoleRepository consoleRepository;

    public GameService(PcRepository pcRepository, ConsoleRepository consoleRepository) {
        this.pcRepository = pcRepository;
        this.consoleRepository = consoleRepository;
    }

    
    //함수
    public void findPcSITE() {
        List<PcDB> a = pcRepository.findBySITEAVAILABILITY("Both");
        System.out.println(a);
    }
    
    //함수
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

    //
    public Page<PcDB> getFullPcList(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return this.pcRepository.findAll(pageable);
    }

    
    /*
    public void findConsoleSITE() {
        List<ConsoleDB> a = consoleRepository.findBySiteavailabilty("Both");
        System.out.println(a);
    }
	*/
    public Page<ConsoleDB> getFullConsoleList(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return this.consoleRepository.findAll(pageable);
    }

    public List<PcDB> getAllData() {
        return pcRepository.findAll();
    }

    public List<ConsoleDB> getAllData_1() {
        return consoleRepository.findAll();
    }

    
    public List<GameDTO> getAllGames() {
        List<PcDB> gameDBList = pcRepository.findAll();
        List<GameDTO> gameDTOList = gameDBList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return gameDTOList;
    }

    
   public List<ConsoleDTO> getAllGames_c() {
        List<ConsoleDB> consoleDBList = consoleRepository.findAll();
        List<ConsoleDTO> consoleDTOList = consoleDBList.stream()
                .map(this::convertToDTO_c)
                .collect(Collectors.toList());

        return consoleDTOList;
    }

    
    
    private GameDTO convertToDTO(PcDB pcDB) {
        GameDTO gameDTO = new GameDTO();
        if (pcDB != null) {
            String price1 = pcDB.getSTEAMPRICE();
            if (price1 != null)
                price1 = price1.replaceAll("[₩,\\s]+", "");
            String price2 = pcDB.getEPICPRICE();
            if (price2 != null)
                price2 = price2.replaceAll("[₩,\\s]+", "");

            if (price2 != null && price1 != null) {
                int price1D = Integer.parseInt(price1);
                int price2D = Integer.parseInt(price2);

                if (price1D > price2D) {
                    gameDTO.setPRICE(pcDB.getEPICPRICE());
                } else if (price1D < price2D) {
                    gameDTO.setPRICE(pcDB.getSTEAMPRICE());
                } else {
                    gameDTO.setPRICE(pcDB.getSTEAMPRICE());
                }
            }
            gameDTO.setPRICE(pcDB.getSTEAMPRICE());
            gameDTO.setTITLE(pcDB.getTITLE());
            gameDTO.setSALEPRICE(pcDB.getSTEAMSALEPRICE());
            gameDTO.setSALEPER(pcDB.getSTEAMSALEPER());
            gameDTO.setDESCRIPTION(pcDB.getSTEAMDESCRIPTION());
            gameDTO.setIMGDATA(pcDB.getSTEAMIMGDATA());
            gameDTO.setGAMEIMG(pcDB.getSTEAMGAMEIMG());
            gameDTO.setURL(pcDB.getSTEAMURL());
            System.out.println(gameDTO.getSALEPER());
        }
        return gameDTO;
    }

    private ConsoleDTO convertToDTO_c(ConsoleDB consoleDB) {
        ConsoleDTO consoleDTO = new ConsoleDTO();
        if (consoleDB != null) {
            String price1 = consoleDB.getPSPRICE();
            if (price1 != null)
                price1 = price1.replaceAll("[원,\\s]+", "");
            String price2 = consoleDB.getSWITCHPRICE();
            if (price2 != null)
                price2 = price2.replaceAll("[원,\\s]+", "");

            if (price2 != null && price1 != null) {
                int price1D = Integer.parseInt(price1);
                int price2D = Integer.parseInt(price2);

                if (price1D > price2D) {
                    consoleDTO.setPRICE(consoleDB.getSWITCHPRICE());
                } else if (price1D < price2D) {
                    consoleDTO.setPRICE(consoleDB.getPSPRICE());
                } else {
                    consoleDTO.setPRICE(consoleDB.getPSPRICE());
                }
            }
            consoleDTO.setNUM(consoleDB.getSWITCHNUM());
            consoleDTO.setTITLE(consoleDB.getTITLE());
            consoleDTO.setSALEPRICE(consoleDB.getPSSALEPRICE());
            consoleDTO.setSALEPER(consoleDB.getPSSALEPER());
            consoleDTO.setDESCRIPTION(consoleDB.getPSDESCRIPTION());
            consoleDTO.setIMGDATA(consoleDB.getPSIMGDATA());
            consoleDTO.setGAMEIMG(consoleDB.getPSGAMEIMG());
            consoleDTO.setURL(consoleDB.getPSURL());
            System.out.println(consoleDB.getTITLE());
        }
        return consoleDTO;
    }
}
