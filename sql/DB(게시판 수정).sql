-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema member
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema member
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `member` DEFAULT CHARACTER SET utf8mb3 ;
USE `member` ;

-- -----------------------------------------------------
-- Table `member`.`info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`info` (
  `ID` VARCHAR(12) NOT NULL,
  `EMAIL` VARCHAR(30) NULL DEFAULT NULL,
  `PWD` CHAR(100) NOT NULL,
  `BIRTH` DATE NULL DEFAULT NULL,
  `GENDER` CHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`bbs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`bbs` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `TITLE` VARCHAR(20) NOT NULL,
  `WRITER` VARCHAR(12) NOT NULL,
  `CATEGORY` VARCHAR(10) NULL DEFAULT NULL,
  `DATE` DATETIME NOT NULL,
  `VIEWS` INT NULL DEFAULT NULL,
  `COMMENT` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `ID_idx` (`ID` ASC) VISIBLE,
  INDEX `UPLOADER` (`WRITER` ASC) VISIBLE,
  CONSTRAINT `UPLOADER`
    FOREIGN KEY (`WRITER`)
    REFERENCES `member`.`info` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`bbs_content`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`bbs_content` (
  `ID` INT NOT NULL,
  `TITLE` VARCHAR(20) NOT NULL,
  `CONTENT` TEXT NULL DEFAULT NULL,
  `CATEGORY` VARCHAR(10) NULL DEFAULT NULL,
  `COMMENT` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `bbs_id`
    FOREIGN KEY (`ID`)
    REFERENCES `member`.`bbs` (`ID`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`news_epic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`news_epic` (
  `TITLE` VARCHAR(100) NOT NULL,
  `RELEASE_DATE` VARCHAR(20) NULL DEFAULT NULL,
  `PRICE` VARCHAR(15) NULL DEFAULT NULL,
  `DESCRIPTION` TEXT NULL DEFAULT NULL,
  `URL` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`TITLE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`news_ps`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`news_ps` (
  `TITLE` VARCHAR(100) NOT NULL,
  `RELEASE_DATE` VARCHAR(20) NULL DEFAULT NULL,
  `PRICE` VARCHAR(15) NULL DEFAULT NULL,
  `DESCRIPTION` TEXT NULL DEFAULT NULL,
  `URL` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`TITLE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`news_steam`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`news_steam` (
  `TITLE` VARCHAR(100) NOT NULL,
  `RELEASE_DATE` VARCHAR(20) NULL DEFAULT NULL,
  `PRICE` VARCHAR(15) NULL DEFAULT NULL,
  `DESCRIPTION` TEXT NULL DEFAULT NULL,
  `URL` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`TITLE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`news_switch`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`news_switch` (
  `TITLE` VARCHAR(100) NOT NULL,
  `RELEASE_DATE` VARCHAR(20) NULL DEFAULT NULL,
  `PRICE` VARCHAR(15) NULL DEFAULT NULL,
  `DESCRIPTION` TEXT NULL DEFAULT NULL,
  `URL` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`TITLE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`prefer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`prefer` (
  `ID` VARCHAR(12) NOT NULL,
  `love` VARCHAR(300) NULL DEFAULT NULL,
  `dislike` VARCHAR(300) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `preferID`
    FOREIGN KEY (`ID`)
    REFERENCES `member`.`info` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`profile_data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`profile_data` (
  `ID` VARCHAR(12) NOT NULL,
  `NICKNAME` CHAR(18) NULL DEFAULT NULL,
  `IMG` TEXT NULL DEFAULT NULL,
  `DESCRIPTION` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `ID`
    FOREIGN KEY (`ID`)
    REFERENCES `member`.`info` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

USE `member` ;

-- -----------------------------------------------------
-- Placeholder table for view `member`.`game_steam_epic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`game_steam_epic` (`steam_num` INT, `epic_num` INT, `title` INT, `site_availability` INT, `steam_price` INT, `epic_price` INT, `steam_saleprice` INT, `epic_saleprice` INT, `steam_saleper` INT, `epic_saleper` INT, `steam_description` INT, `epic_description` INT, `steam_imgdata` INT, `epic_imgdata` INT, `steam_gameimg` INT, `epic_gameimg` INT, `steam_url` INT, `epic_url` INT);

-- -----------------------------------------------------
-- Placeholder table for view `member`.`game_switch_ps`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`game_switch_ps` (`ps_num` INT, `switch_num` INT, `title` INT, `site_availability` INT, `ps_price` INT, `switch_price` INT, `ps_saleprice` INT, `switch_saleprice` INT, `ps_saleper` INT, `switch_saleper` INT, `ps_description` INT, `switch_description` INT, `ps_imgdata` INT, `switch_imgdata` INT, `ps_gameimg` INT, `switch_gameimg` INT, `ps_url` INT, `switch_url` INT);

-- -----------------------------------------------------
-- View `member`.`game_steam_epic`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `member`.`game_steam_epic`;
USE `member`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `member`.`game_steam_epic` AS select `s`.`NUM` AS `steam_num`,`e`.`NUM` AS `epic_num`,`s`.`TITLE` AS `title`,(case when ((`s`.`TITLE` is not null) and (`e`.`TITLE` is not null)) then 'Both' when (`s`.`TITLE` is not null) then 'Steam Only' when (`e`.`TITLE` is not null) then 'Epic Only' else 'None' end) AS `site_availability`,`s`.`PRICE` AS `steam_price`,`e`.`PRICE` AS `epic_price`,`s`.`SALEPRICE` AS `steam_saleprice`,`e`.`SALEPRICE` AS `epic_saleprice`,`s`.`SALEPER` AS `steam_saleper`,`e`.`SALEPER` AS `epic_saleper`,`s`.`DESCRIPTION` AS `steam_description`,`e`.`DESCRIPTION` AS `epic_description`,`s`.`IMGDATA` AS `steam_imgdata`,`e`.`IMGDATA` AS `epic_imgdata`,`s`.`GAMEIMG` AS `steam_gameimg`,`e`.`GAMEIMG` AS `epic_gameimg`,`s`.`URL` AS `steam_url`,`e`.`URL` AS `epic_url` from (`member`.`gamedata_steam` `s` left join `member`.`gamedata_epic` `e` on((`s`.`TITLE` = `e`.`TITLE`))) union select `s`.`NUM` AS `steam_num`,`e`.`NUM` AS `epic_num`,`e`.`TITLE` AS `title`,(case when ((`s`.`TITLE` is not null) and (`e`.`TITLE` is not null)) then 'Both' when (`s`.`TITLE` is not null) then 'Steam Only' when (`e`.`TITLE` is not null) then 'Epic Only' else 'None' end) AS `site_availability`,`s`.`PRICE` AS `steam_price`,`e`.`PRICE` AS `epic_price`,`s`.`SALEPRICE` AS `steam_saleprice`,`e`.`SALEPRICE` AS `epic_saleprice`,`s`.`SALEPER` AS `steam_saleper`,`e`.`SALEPER` AS `epic_saleper`,`s`.`DESCRIPTION` AS `steam_description`,`e`.`DESCRIPTION` AS `epic_description`,`s`.`IMGDATA` AS `steam_imgdata`,`e`.`IMGDATA` AS `epic_imgdata`,`s`.`GAMEIMG` AS `steam_gameimg`,`e`.`GAMEIMG` AS `epic_gameimg`,`s`.`URL` AS `steam_url`,`e`.`URL` AS `epic_url` from (`member`.`gamedata_epic` `e` left join `member`.`gamedata_steam` `s` on((`s`.`TITLE` = `e`.`TITLE`)));

-- -----------------------------------------------------
-- View `member`.`game_switch_ps`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `member`.`game_switch_ps`;
USE `member`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `member`.`game_switch_ps` AS select `p`.`NUM` AS `ps_num`,`s`.`NUM` AS `switch_num`,`p`.`TITLE` AS `title`,(case when ((`p`.`TITLE` is not null) and (`s`.`TITLE` is not null)) then 'Both' when (`p`.`TITLE` is not null) then 'PS Only' when (`s`.`TITLE` is not null) then 'Switch Only' else 'None' end) AS `site_availability`,`p`.`PRICE` AS `ps_price`,`s`.`PRICE` AS `switch_price`,`p`.`SALEPRICE` AS `ps_saleprice`,`s`.`SALEPRICE` AS `switch_saleprice`,`p`.`SALEPER` AS `ps_saleper`,`s`.`SALEPER` AS `switch_saleper`,`p`.`DESCRIPTION` AS `ps_description`,`s`.`DESCRIPTION` AS `switch_description`,`p`.`IMGDATA` AS `ps_imgdata`,`s`.`IMGDATA` AS `switch_imgdata`,`p`.`GAMEIMG` AS `ps_gameimg`,`s`.`GAMEIMG` AS `switch_gameimg`,`p`.`URL` AS `ps_url`,`s`.`URL` AS `switch_url` from (`member`.`gamedata_ps` `p` left join `member`.`gamedata_switch` `s` on((`p`.`TITLE` = `s`.`TITLE`))) union select `p`.`NUM` AS `ps_num`,`s`.`NUM` AS `switch_num`,`p`.`TITLE` AS `title`,(case when ((`p`.`TITLE` is not null) and (`s`.`TITLE` is not null)) then 'Both' when (`p`.`TITLE` is not null) then 'PS Only' when (`s`.`TITLE` is not null) then 'Switch Only' else 'None' end) AS `site_availability`,`p`.`PRICE` AS `ps_price`,`s`.`PRICE` AS `switch_price`,`p`.`SALEPRICE` AS `ps_saleprice`,`s`.`SALEPRICE` AS `switch_saleprice`,`p`.`SALEPER` AS `ps_saleper`,`s`.`SALEPER` AS `switch_saleper`,`p`.`DESCRIPTION` AS `ps_description`,`s`.`DESCRIPTION` AS `switch_description`,`p`.`IMGDATA` AS `ps_imgdata`,`s`.`IMGDATA` AS `switch_imgdata`,`p`.`GAMEIMG` AS `ps_gameimg`,`s`.`GAMEIMG` AS `switch_gameimg`,`p`.`URL` AS `ps_url`,`s`.`URL` AS `switch_url` from (`member`.`gamedata_ps` `p` left join `member`.`gamedata_switch` `s` on((`p`.`TITLE` = `s`.`TITLE`)));

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
