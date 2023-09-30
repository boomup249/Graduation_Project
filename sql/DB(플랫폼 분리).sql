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
  `TITLE` VARCHAR(20) NOT NULL,
  `ID` VARCHAR(12) NOT NULL,
  `TIME` DATETIME NOT NULL,
  `CONTENTS` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`TITLE`),
  INDEX `ID_idx` (`ID` ASC) VISIBLE,
  CONSTRAINT `UPID`
    FOREIGN KEY (`ID`)
    REFERENCES `member`.`info` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`data` (
  `ID` VARCHAR(12) NOT NULL,
  `NICKNAME` CHAR(18) NULL DEFAULT NULL,
  `LIKE` INT NULL DEFAULT NULL,
  `DISLIKE` INT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `ID`
    FOREIGN KEY (`ID`)
    REFERENCES `member`.`info` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`gamedata_epic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`gamedata_epic` (
  `NUM` INT NOT NULL,
  `TITLE` VARCHAR(40) NULL DEFAULT NULL,
  `PRICE` VARCHAR(15) NULL DEFAULT NULL,
  `SALEPRICE` VARCHAR(15) NULL DEFAULT NULL,
  `SALEPER` VARCHAR(5) NULL DEFAULT NULL,
  `DESCRIPTION` TEXT NULL DEFAULT NULL,
  `IMGDATA` TEXT NULL DEFAULT NULL,
  `GAMEIMG` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`NUM`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`gamedata_ps`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`gamedata_ps` (
  `NUM` INT NOT NULL,
  `TITLE` VARCHAR(40) NULL DEFAULT NULL,
  `PLATFORM` VARCHAR(10) NULL DEFAULT NULL,
  `PRICE` VARCHAR(15) NULL DEFAULT NULL,
  `SALEPRICE` VARCHAR(15) NULL DEFAULT NULL,
  `SALEPER` VARCHAR(5) NULL DEFAULT NULL,
  `DESCRIPTION` TEXT NULL DEFAULT NULL,
  `IMGDATA` TEXT NULL DEFAULT NULL,
  `GAMEIMG` TEXT NULL DEFAULT NULL,
  `URL` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`NUM`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`gamedata_steam`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`gamedata_steam` (
  `NUM` INT NOT NULL,
  `TITLE` VARCHAR(40) NULL DEFAULT NULL,
  `PLATFORM` VARCHAR(10) NULL DEFAULT NULL,
  `PRICE` VARCHAR(15) NULL DEFAULT NULL,
  `SALEPRICE` VARCHAR(15) NULL DEFAULT NULL,
  `SALEPER` VARCHAR(5) NULL DEFAULT NULL,
  `DESCRIPTION` TEXT NULL DEFAULT NULL,
  `IMGDATA` TEXT NULL DEFAULT NULL,
  `GAMEIMG` TEXT NULL DEFAULT NULL,
  `URL` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`NUM`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`gamedata_switch`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`gamedata_switch` (
  `NUM` INT NOT NULL,
  `TITLE` VARCHAR(40) NULL DEFAULT NULL,
  `PLATFORM` VARCHAR(10) NULL DEFAULT NULL,
  `PRICE` VARCHAR(15) NULL DEFAULT NULL,
  `SALEPRICE` VARCHAR(15) NULL DEFAULT NULL,
  `SALEPER` VARCHAR(5) NULL DEFAULT NULL,
  `DESCRIPTION` TEXT NULL DEFAULT NULL,
  `IMGDATA` TEXT NULL DEFAULT NULL,
  `GAMEIMG` TEXT NULL DEFAULT NULL,
  `URL` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`NUM`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`gamedata_genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`gamedata_genre` (
  `NUM` INT NOT NULL,
  `TITLE` VARCHAR(40) NULL DEFAULT NULL,
  `PLATFORM` VARCHAR(10) NULL DEFAULT NULL,
  `genre` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`NUM`),
  CONSTRAINT `epic_num`
    FOREIGN KEY (`NUM`)
    REFERENCES `member`.`gamedata_epic` (`NUM`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `ps_num`
    FOREIGN KEY (`NUM`)
    REFERENCES `member`.`gamedata_ps` (`NUM`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `steam_num`
    FOREIGN KEY (`NUM`)
    REFERENCES `member`.`gamedata_steam` (`NUM`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `switch_num`
    FOREIGN KEY (`NUM`)
    REFERENCES `member`.`gamedata_switch` (`NUM`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`prefer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`prefer` (
  `ID` VARCHAR(12) NOT NULL,
  `action` TINYINT(1) NULL DEFAULT NULL,
  `shooting` TINYINT NULL DEFAULT NULL,
  `adventure` TINYINT NULL DEFAULT NULL,
  `fighting` TINYINT NULL DEFAULT NULL,
  `roguelike` TINYINT NULL DEFAULT NULL,
  `RPG` TINYINT NULL DEFAULT NULL,
  `MMORPG` TINYINT NULL DEFAULT NULL,
  `simulation` TINYINT NULL DEFAULT NULL,
  `sports` TINYINT NULL DEFAULT NULL,
  `puzzle` TINYINT NULL DEFAULT NULL,
  `arcade` TINYINT NULL DEFAULT NULL,
  `strat` TINYINT NULL DEFAULT NULL,
  `horror` TINYINT NULL DEFAULT NULL,
  `multi` TINYINT NULL DEFAULT NULL,
  `single` TINYINT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `preferID`
    FOREIGN KEY (`ID`)
    REFERENCES `member`.`info` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
