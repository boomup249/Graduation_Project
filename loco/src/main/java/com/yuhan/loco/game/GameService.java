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
    @Autowired
    public GameService(PcRepository pcRepository, ConsoleRepository consoleRepository) {
        this.pcRepository = pcRepository;
        this.consoleRepository = consoleRepository;
    }

    public PcDB getPcByKey(String key) {
        return pcRepository.findByKEY(key);
    }
    
    //함수
    public void findPcSITE() {
        List<PcDB> a = pcRepository.findBySITEAVAILABILITY("Both");
        System.out.println(a);
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
    
    public GameDTO createToDTO(String key, PcDB pcDB) {
        GameDTO gameDTO = new GameDTO();
        if (key != null) {
            PcDB selectedPcGame = pcRepository.findByKEY(key);
            if (selectedPcGame != null) {

             // TITLE 설정
                gameDTO.setTITLE(selectedPcGame.getTITLE());
                gameDTO.setSITEAVAILABILITY(pcDB.getSITEAVAILABILITY());
     
                if ("Steam Only".equalsIgnoreCase(pcDB.getSITEAVAILABILITY())) {
                    gameDTO.setIMGDATA(pcDB.getSTEAMIMGDATA());
                    gameDTO.setGAMEIMG(pcDB.getSTEAMGAMEIMG());
                    gameDTO.setDESCRIPTION(pcDB.getSTEAMDESCRIPTION());
                    gameDTO.setURL(pcDB.getSTEAMURL());
                    System.out.println(pcDB.getSTEAMURL());
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
                    gameDTO.setURL(pcDB.getSTEAMURL());
                    gameDTO.setURL(pcDB.getEPICURL());
                }             
                return gameDTO;
            }
        }
        return gameDTO;
    }
}