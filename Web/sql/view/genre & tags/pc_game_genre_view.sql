CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `member`.`pc_game_genre` AS
    SELECT 
        `member`.`gamedata_steam_genre`.`NUM` AS `NUM`,
        `member`.`gamedata_steam_genre`.`TITLE` AS `TITLE`,
        `member`.`gamedata_steam_genre`.`genre` AS `GENRE`
    FROM
        `member`.`gamedata_steam_genre`
    WHERE
        (`member`.`gamedata_steam_genre`.`genre` IN ('액션' , 'RPG',
            '슈팅',
            '전략',
            '어드벤처',
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
    UNION SELECT 
        ((SELECT 
                IFNULL(MAX(`member`.`gamedata_steam_genre`.`NUM`), 0)
            FROM
                `member`.`gamedata_steam_genre`) + `member`.`gamedata_epic_genre`.`NUM`) AS `NUM`,
        `member`.`gamedata_epic_genre`.`TITLE` AS `TITLE`,
        `member`.`gamedata_epic_genre`.`GENRE` AS `GENRE`
    FROM
        `member`.`gamedata_epic_genre`
    WHERE
        (`member`.`gamedata_epic_genre`.`GENRE` IN ('액션' , 'RPG',
            '슈팅',
            '전략',
            '어드벤처',
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