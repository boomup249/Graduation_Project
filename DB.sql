-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema member
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema member
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `member` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema gamedata
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema gamedata
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gamedata` ;
USE `member` ;

-- -----------------------------------------------------
-- Table `member`.`info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`info` (
  `ID` VARCHAR(12) NOT NULL,
  `PWD` CHAR(16) NOT NULL,
  `BIRTH` DATE NULL,
  `GENDER` CHAR(1) NULL,
  `GENRE1` CHAR(12) NULL,
  `GENRE2` CHAR(12) NULL,
  `GENRE3` CHAR(12) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `member`.`data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`data` (
  `ID` VARCHAR(12) NOT NULL,
  `NICKNAME` CHAR(18) NULL,
  `LIKE` INT NULL,
  `DISLIKE` INT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `ID`
    FOREIGN KEY (`ID`)
    REFERENCES `member`.`info` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `member`.`bbs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`bbs` (
  `TITLE` VARCHAR(20) NOT NULL,
  `ID` VARCHAR(12) NOT NULL,
  `TIME` DATETIME NOT NULL,
  `CONTENTS` TEXT(400) NULL,
  PRIMARY KEY (`TITLE`),
  INDEX `ID_idx` (`ID` ASC) VISIBLE,
  constraint UPID foreign key (id) references member.info(id) on delete cascade on update cascade)
ENGINE = InnoDB;

USE `gamedata` ;

-- -----------------------------------------------------
-- Table `gamedata`.`MOBILE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gamedata`.`MOBILE` (
  `TITLE` VARCHAR(40) NOT NULL,
  `DESCRIPTION` VARCHAR(200) NULL,
  `IMAGE` VARCHAR(100) NULL,
  `PRICE` INT NULL,
  PRIMARY KEY (`TITLE`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gamedata`.`PC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gamedata`.`PC` (
  `TITLE` VARCHAR(40) NOT NULL,
  `DESCRIPTION` VARCHAR(200) NULL,
  `IMAGE` VARCHAR(100) NULL,
  `PRICE` INT NULL,
  PRIMARY KEY (`TITLE`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gamedata`.`GENRE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gamedata`.`GENRE` (
  `TITLE` VARCHAR(40) NOT NULL,
  `FPS` INT NULL,
  `RPG` INT NULL,
  `STRAT` INT NULL,
  `RACING` INT NULL,
  `SIMUL` INT NULL,
  PRIMARY KEY (`TITLE`),
  CONSTRAINT `MTITLE`
    FOREIGN KEY (`TITLE`)
    REFERENCES `gamedata`.`MOBILE` (`TITLE`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `PCTITLE`
    FOREIGN KEY (`TITLE`)
    REFERENCES `gamedata`.`PC` (`TITLE`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
