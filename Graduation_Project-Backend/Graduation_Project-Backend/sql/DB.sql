-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema gamedata
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema gamedata
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gamedata` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
-- -----------------------------------------------------
-- Schema member
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema member
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `member` DEFAULT CHARACTER SET utf8mb3 ;
USE `gamedata` ;

-- -----------------------------------------------------
-- Table `gamedata`.`mobile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gamedata`.`mobile` (
  `TITLE` VARCHAR(40) NOT NULL,
  `DESCRIPTION` VARCHAR(200) NULL DEFAULT NULL,
  `IMAGE` VARCHAR(100) NULL DEFAULT NULL,
  `PRICE` INT NULL DEFAULT NULL,
  PRIMARY KEY (`TITLE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `gamedata`.`pc`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gamedata`.`pc` (
  `TITLE` VARCHAR(40) NOT NULL,
  `DESCRIPTION` VARCHAR(200) NULL DEFAULT NULL,
  `IMAGE` VARCHAR(100) NULL DEFAULT NULL,
  `PRICE` INT NULL DEFAULT NULL,
  PRIMARY KEY (`TITLE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `gamedata`.`genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gamedata`.`genre` (
  `TITLE` VARCHAR(40) NOT NULL,
  `FPS` INT NULL DEFAULT NULL,
  `RPG` INT NULL DEFAULT NULL,
  `STRAT` INT NULL DEFAULT NULL,
  `RACING` INT NULL DEFAULT NULL,
  `SIMUL` INT NULL DEFAULT NULL,
  PRIMARY KEY (`TITLE`),
  CONSTRAINT `MTITLE`
    FOREIGN KEY (`TITLE`)
    REFERENCES `gamedata`.`mobile` (`TITLE`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `PCTITLE`
    FOREIGN KEY (`TITLE`)
    REFERENCES `gamedata`.`pc` (`TITLE`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `member` ;

-- -----------------------------------------------------
-- Table `member`.`info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`info` (
  `ID` VARCHAR(12) NOT NULL,
  `EMAIL` VARCHAR(30) NULL DEFAULT NULL,
  `PWD` CHAR(16) NOT NULL,
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
-- Table `member`.`prefer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`prefer` (
  `ID` VARCHAR(12) NOT NULL,
  `action` TINYINT(1) NULL DEFAULT NULL,
  `action_adventure` TINYINT(1) NULL DEFAULT NULL,
  `survival` TINYINT(1) NULL DEFAULT NULL,
  `shooting` TINYINT(1) NULL DEFAULT NULL,
  `FPS` TINYINT(1) NULL DEFAULT NULL,
  `RPG` TINYINT(1) NULL DEFAULT NULL,
  `ARPG` TINYINT(1) NULL DEFAULT NULL,
  `MMORPG` TINYINT(1) NULL DEFAULT NULL,
  `open_world` TINYINT(1) NULL DEFAULT NULL,
  `hack_and_slash` TINYINT(1) NULL DEFAULT NULL,
  `adventure` TINYINT(1) NULL DEFAULT NULL,
  `sports` TINYINT(1) NULL DEFAULT NULL,
  `racing` TINYINT(1) NULL DEFAULT NULL,
  `casual` TINYINT(1) NULL DEFAULT NULL,
  `puzzle` TINYINT(1) NULL DEFAULT NULL,
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
