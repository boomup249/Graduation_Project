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
-- Table `member`.`gamedata_steam`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`gamedata_steam` (
  `NUM` INT NOT NULL AUTO_INCREMENT,
  `TITLE` VARCHAR(100) NULL DEFAULT NULL,
  `PRICE` VARCHAR(15) NULL DEFAULT NULL,
  `SALEPRICE` VARCHAR(15) NULL DEFAULT NULL,
  `SALEPER` VARCHAR(5) NULL DEFAULT NULL,
  `DESCRIPTION` TEXT NULL DEFAULT NULL,
  `IMGDATA` TEXT NULL DEFAULT NULL,
  `GAMEIMG` TEXT NULL DEFAULT NULL,
  `URL` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`NUM`),
  UNIQUE INDEX `TITLE_UNIQUE` (`TITLE` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`gamedata_genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`gamedata_genre` (
  `NUM` INT NOT NULL AUTO_INCREMENT,
  `TITLE` VARCHAR(100) NULL DEFAULT NULL,
  `genre` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`NUM`),
  UNIQUE INDEX `TITLE_UNIQUE` (`TITLE` ASC) VISIBLE,
  CONSTRAINT `steam_title`
    FOREIGN KEY (`TITLE`)
    REFERENCES `member`.`gamedata_steam` (`TITLE`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`prefer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`prefer` (
  `ID` VARCHAR(12) NOT NULL,
  `action` VARCHAR(5) NULL DEFAULT NULL,
  `shooting` VARCHAR(5) NULL DEFAULT NULL,
  `adventure` VARCHAR(5) NULL DEFAULT NULL,
  `fighting` VARCHAR(5) NULL DEFAULT NULL,
  `roguelike` VARCHAR(5) NULL DEFAULT NULL,
  `RPG` VARCHAR(5) NULL DEFAULT NULL,
  `MMORPG` VARCHAR(5) NULL DEFAULT NULL,
  `simulation` VARCHAR(5) NULL DEFAULT NULL,
  `sports` VARCHAR(5) NULL DEFAULT NULL,
  `puzzle` VARCHAR(5) NULL DEFAULT NULL,
  `arcade` VARCHAR(5) NULL DEFAULT NULL,
  `strat` VARCHAR(5) NULL DEFAULT NULL,
  `horror` VARCHAR(5) NULL DEFAULT NULL,
  `multi` VARCHAR(5) NULL DEFAULT NULL,
  `single` VARCHAR(5) NULL DEFAULT NULL,
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
