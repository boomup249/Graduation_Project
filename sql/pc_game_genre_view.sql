CREATE VIEW `member`.`pc_game_genre` AS
	SELECT
		`NUM` AS `NUM`,
        `TITLE` AS `TITLE`,
        `genre` AS `GENRE`
	FROM `member`.`gamedata_steam_genre`
    WHERE
        (`genre` IN 
			('액션' , 
            'RPG',
            '슈팅',
            '전략',
            '어드벤쳐',
            '시뮬레이션',
            '스포츠',
            '로그라이크',
            '퍼즐',
            '격투',
            '아케이드',
            '공포',
            '싱글 플레이어',
            '멀티플레이어',
            'MMORPG')) 
    
    UNION
    SELECT
		((SELECT 
                MAX(`member`.`gamedata_steam_genre`.`NUM`)
            FROM
                `member`.`gamedata_steam_genre`) + `member`.`gamedata_epic_genre`.`NUM`) AS `NUM`,
        `TITLE` AS `TITLE`,
        `GENRE` AS `GENRE`
	FROM `member`.`gamedata_epic_genre`
    WHERE
        (`genre` IN 
			('액션' , 
            'RPG',
            '슈팅',
            '전략',
            '어드벤쳐',
            '시뮬레이션',
            '스포츠',
            '로그라이크',
            '퍼즐',
            '격투',
            '아케이드',
            '공포',
            '싱글 플레이어',
            '멀티플레이어',
            'MMORPG')) ;