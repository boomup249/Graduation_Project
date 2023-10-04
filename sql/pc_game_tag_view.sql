CREATE VIEW `member`.`pc_game_tag` AS
	SELECT
		`NUM` AS `NUM`,
        `TITLE` AS `TITLE`,
        `genre` AS `GENRE`
	FROM `member`.`gamedata_steam_genre`
    
    UNION
    SELECT
		((SELECT 
                MAX(`member`.`gamedata_steam_genre`.`NUM`)
            FROM
                `member`.`gamedata_steam_genre`) + `member`.`gamedata_epic_genre`.`NUM`) AS `NUM`,
        `TITLE` AS `TITLE`,
        `GENRE` AS `GENRE`
	FROM `member`.`gamedata_epic_genre`;