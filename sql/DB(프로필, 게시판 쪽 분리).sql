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
-- Table `member`.`bbs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`bbs` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `TITLE` VARCHAR(20) NOT NULL,
  `WRITER` VARCHAR(12) NOT NULL,
  `CATEGORY` VARCHAR(10) NULL DEFAULT NULL,
  `DATE` VARCHAR(30) NOT NULL,
  `VIEWS` INT NULL DEFAULT NULL,
  `COMMENT` INT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `ID_idx` (`ID` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`bbs_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`bbs_comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `post_id` INT NULL DEFAULT NULL,
  `comment` TEXT NULL DEFAULT NULL,
  `writer` VARCHAR(12) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `post_id_idx` (`post_id` ASC) VISIBLE,
  CONSTRAINT `post_id`
    FOREIGN KEY (`post_id`)
    REFERENCES `member`.`bbs` (`ID`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `member`.`bbs_content`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member`.`bbs_content` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `BBS_ID` INT NULL DEFAULT NULL,
  `CATEGORY` VARCHAR(10) NOT NULL,
  `TITLE` VARCHAR(20) NOT NULL,
  `WRITER` VARCHAR(12) NOT NULL,
  `CONTENT` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `uploader_idx` (`WRITER` ASC) VISIBLE,
  INDEX `bbs_id_idx` (`BBS_ID` ASC) VISIBLE,
  CONSTRAINT `bbs_id`
    FOREIGN KEY (`BBS_ID`)
    REFERENCES `member`.`bbs` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


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


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
