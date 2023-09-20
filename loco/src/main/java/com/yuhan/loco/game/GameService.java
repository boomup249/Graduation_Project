package com.yuhan.loco.game;

import java.sql.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GameService {
   private final GameRepository gameRepository;
   GameDB game = new GameDB();
   
   public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
   
   public GameDB create(String TITLE, String PRICE, String SALEPRICE, String SALEPER, String DESCRIPTION,String IMGDATA, String GAMEIMG, String URL) {
      game.setTITLE(TITLE);
      game.setPRICE(PRICE);
      game.setSALEPRICE(SALEPRICE);
      game.setSALEPER(SALEPER);
      game.setDESCRIPTION(DESCRIPTION);
      game.setIMGDATA(IMGDATA);
      game.setGAMEIMG(GAMEIMG);
      game.setURL(URL);
      

      System.out.println("----repository----");
      System.out.println(game.getNUM());
      System.out.println(game.getPRICE());
      System.out.println(game.getSALEPRICE());
      System.out.println(game.getSALEPER());
      System.out.println(game.getDESCRIPTION());
      System.out.println(game.getIMGDATA());
      System.out.println(game.getGAMEIMG());
      System.out.println(game.getURL());
      
      
      this.gameRepository.save(game);   
      return game;
   }
   
  
}