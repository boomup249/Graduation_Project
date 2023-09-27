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
    private final PcRepository pcRepository;
    private final ConsoleRepository consoleRepository;

    @Autowired
    public GameService(PcRepository pcRepository, ConsoleRepository consoleRepository) {
        this.pcRepository = pcRepository;
        this.consoleRepository = consoleRepository;
    }

    public void findPcSITE() {
        List<PcDB> a = pcRepository.findBySITEAVAILABILITY("Both");
        System.out.println(a);
    }

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

    
    public List<GameDTO> getAllGames_c() {
        List<ConsoleDB> gameDBList = consoleRepository.findAll();
        List<GameDTO> gameDTOList = gameDBList.stream()
                .map(this::convertToDTO_c)
                .collect(Collectors.toList());

        return gameDTOList;
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

    private GameDTO convertToDTO_c(ConsoleDB consoleDB) {
        GameDTO gameDTO = new GameDTO();
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
                    gameDTO.setPRICE(consoleDB.getSWITCHPRICE());
                } else if (price1D < price2D) {
                    gameDTO.setPRICE(consoleDB.getPSPRICE());
                } else {
                    gameDTO.setPRICE(consoleDB.getPSPRICE());
                }
            }
            gameDTO.setNUM(consoleDB.getSWITCHNUM());
            gameDTO.setTITLE(consoleDB.getTITLE());
            gameDTO.setSALEPRICE(consoleDB.getPSSALEPRICE());
            gameDTO.setSALEPER(consoleDB.getPSSALEPER());
            gameDTO.setDESCRIPTION(consoleDB.getPSDESCRIPTION());
            gameDTO.setIMGDATA(consoleDB.getPSIMGDATA());
            gameDTO.setGAMEIMG(consoleDB.getPSGAMEIMG());
            gameDTO.setURL(consoleDB.getPSURL());
            System.out.println(consoleDB.getTITLE());
        }
        return gameDTO;
    }
}
