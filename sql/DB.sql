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
-- Table `member`.`gamedata_console`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`gamedata_console` (
  `TITLE` VARCHAR(40) NOT NULL,
  `DESCRIPTION` VARCHAR(200) NULL DEFAULT NULL,
  `IMAGE` VARCHAR(100) NULL DEFAULT NULL,
  `PRICE` INT NULL DEFAULT NULL,
  PRIMARY KEY (`TITLE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`gamedata_pc`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`gamedata_pc` (
  `TITLE` VARCHAR(40) NOT NULL,
  `DESCRIPTION` VARCHAR(200) NULL DEFAULT NULL,
  `IMAGE` VARCHAR(100) NULL DEFAULT NULL,
  `PRICE` INT NULL DEFAULT NULL,
  PRIMARY KEY (`TITLE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`gamedata_genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`gamedata_genre` (
  `TITLE` VARCHAR(40) NOT NULL,
  `action` INT NULL DEFAULT NULL,
  `shooting` INT NULL DEFAULT NULL,
  `adventure` INT NULL DEFAULT NULL,
  `fighting` INT NULL DEFAULT NULL,
  `roguelike` INT NULL DEFAULT NULL,
  `MMORPG` INT NULL DEFAULT NULL,
  `simulation` INT NULL DEFAULT NULL,
  `sports` INT NULL DEFAULT NULL,
  `puzzle` INT NULL DEFAULT NULL,
  `arcade` INT NULL DEFAULT NULL,
  `horror` INT NULL DEFAULT NULL,
  `multi` INT NULL DEFAULT NULL,
  `single` INT NULL DEFAULT NULL,
  PRIMARY KEY (`TITLE`),
  CONSTRAINT `CONSOLE_TITLE`
    FOREIGN KEY (`TITLE`)
    REFERENCES `member`.`gamedata_console` (`TITLE`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `PCTITLE`
    FOREIGN KEY (`TITLE`)
    REFERENCES `member`.`gamedata_pc` (`TITLE`)
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
