CREATE VIEW `member`.`console_game_tag` AS
	SELECT
		`NUM` AS `NUM`,
        `TITLE` AS `TITLE`,
        `genre` AS `GENRE`
	FROM `member`.`gamedata_ps_genre`
    
    UNION
    SELECT
		((SELECT 
                IFNULL(MAX(`member`.`gamedata_ps_genre`.`NUM`), 0)
            FROM
                `member`.`gamedata_ps_genre`) + `member`.`gamedata_switch_genre`.`NUM`) AS `NUM`,
        `TITLE` AS `TITLE`,
        `GENRE` AS `GENRE`
	FROM `member`.`gamedata_switch_genre`;