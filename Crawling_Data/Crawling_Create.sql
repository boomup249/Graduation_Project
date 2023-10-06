DROP TABLE IF EXISTS gamedata_switch_genre;
DROP TABLE IF EXISTS gamedata_switch;
DROP TABLE IF EXISTS gamedata_ps_genre;
DROP TABLE IF EXISTS gamedata_ps;
DROP TABLE IF EXISTS gamedata_steam_genre;
DROP TABLE IF EXISTS gamedata_steam;
DROP TABLE IF EXISTS gamedata_epic_genre;
DROP TABLE IF EXISTS gamedata_epic;
DROP TABLE IF EXISTS release_info;

CREATE TABLE IF NOT EXISTS gamedata_switch (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                                `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                                `PRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                                `SALEPRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                                `SALEPER` VARCHAR(5) NULL DEFAULT NULL,
                                                                `DESCRIPTION` TEXT NULL DEFAULT NULL,
                                                                `IMGDATA` TEXT NULL DEFAULT NULL,
                                                                `GAMEIMG` TEXT NULL DEFAULT NULL,
                                                                `URL` TEXT NULL DEFAULT NULL,
																`VARIA` TINYINT(1) NOT NULL DEFAULT 1,
                                                                PRIMARY KEY (`NUM`),
                                                                UNIQUE KEY (`TITLE`));
                                                                
CREATE TABLE IF NOT EXISTS gamedata_switch_genre (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                                        `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                                        `GENRE` VARCHAR(30) NULL DEFAULT NULL,
                                                                        PRIMARY KEY (`NUM`),
                                                                        CONSTRAINT `switch_title`
                                                                            FOREIGN KEY (`TITLE`)
                                                                            REFERENCES `gamedata_switch` (`TITLE`)
                                                                            ON DELETE CASCADE
                                                                            ON UPDATE CASCADE);
                                                                            
CREATE TABLE IF NOT EXISTS gamedata_ps (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                            `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                            `PRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                            `SALEPRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                            `SALEPER` VARCHAR(5) NULL DEFAULT NULL,
                                                            `DESCRIPTION` TEXT NULL DEFAULT NULL,
                                                            `IMGDATA` TEXT NULL DEFAULT NULL,
                                                            `GAMEIMG` TEXT NULL DEFAULT NULL,
                                                            `URL` TEXT NULL DEFAULT NULL,
                                                            `VARIA` TINYINT(1) NOT NULL DEFAULT 1,
                                                            PRIMARY KEY (`NUM`),
                                                            UNIQUE KEY (`TITLE`));
                                                            
CREATE TABLE IF NOT EXISTS gamedata_ps_genre (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                                    `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                                    `genre` VARCHAR(30) NULL DEFAULT NULL,
                                                                    PRIMARY KEY (`NUM`),
                                                                    CONSTRAINT `ps_title`
                                                                        FOREIGN KEY (`TITLE`)
                                                                        REFERENCES `gamedata_ps` (`TITLE`)
                                                                        ON DELETE CASCADE
                                                                        ON UPDATE CASCADE);
                                                                        
CREATE TABLE IF NOT EXISTS gamedata_steam (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                                `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                                `PRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                                `SALEPRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                                `SALEPER` VARCHAR(5) NULL DEFAULT NULL,
                                                                `DESCRIPTION` TEXT NULL DEFAULT NULL,
                                                                `IMGDATA` TEXT NULL DEFAULT NULL,
                                                                `GAMEIMG` TEXT NULL DEFAULT NULL,
                                                                `URL` TEXT NULL DEFAULT NULL,
                                                                `VARIA` TINYINT(1) NOT NULL DEFAULT 1,
                                                                PRIMARY KEY (`NUM`),
                                                                UNIQUE KEY (`TITLE`));
                                                                
CREATE TABLE IF NOT EXISTS gamedata_steam_genre (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                                    `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                                    `genre` VARCHAR(30) NULL DEFAULT NULL,
                                                                    PRIMARY KEY (`NUM`),
                                                                    CONSTRAINT `steam_title`
                                                                        FOREIGN KEY (`TITLE`)
                                                                        REFERENCES `gamedata_steam` (`TITLE`)
                                                                        ON DELETE CASCADE
                                                                        ON UPDATE CASCADE);
                                                                        
CREATE TABLE IF NOT EXISTS gamedata_epic (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                                `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                                `PRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                                `SALEPRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                                `SALEPER` VARCHAR(5) NULL DEFAULT NULL,
                                                                `DESCRIPTION` TEXT NULL DEFAULT NULL,
                                                                `IMGDATA` TEXT NULL DEFAULT NULL,
                                                                `GAMEIMG` TEXT NULL DEFAULT NULL,
                                                                `URL` TEXT NULL DEFAULT NULL,
                                                                `VARIA` TINYINT(1) NOT NULL DEFAULT 1,
                                                                PRIMARY KEY (`NUM`),
                                                                UNIQUE KEY (`TITLE`));
                                                                
CREATE TABLE IF NOT EXISTS gamedata_epic_genre (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                                `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                                `GENRE` VARCHAR(30) NULL DEFAULT NULL,
                                                                PRIMARY KEY (`NUM`),
                                                                CONSTRAINT `epic_title`
                                                                    FOREIGN KEY (`TITLE`)
                                                                    REFERENCES `gamedata_epic` (`TITLE`)
                                                                    ON DELETE CASCADE
                                                                    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS release_info (`DATE` VARCHAR(15) NULL DEFAULT NULL,
										 `TITLE` VARCHAR(100) NOT NULL,
										 `PLATFORM` VARCHAR(15) NULL DEFAULT NULL,
										 `PRICE` VARCHAR(15) NULL DEFAULT NULL,
										 `VARIA` TINYINT(1) NULL DEFAULT 1,
										 PRIMARY KEY (`TITLE`)
            );
