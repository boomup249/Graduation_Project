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
  `ID` INT NOT NULL AUTO_INCREMENT,
  `CATEGORY` VARCHAR(10) NOT NULL,
  `TITLE` VARCHAR(20) NOT NULL,
  `WRITER` VARCHAR(12) NOT NULL,
  `CONTENT` TEXT NULL DEFAULT NULL,
  `COMMENT` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `uploader_idx` (`WRITER` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
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


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
