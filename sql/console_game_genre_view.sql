CREATE 
VIEW `member`.`console_game_genre` AS
    SELECT 
        `member`.`gamedata_ps_genre`.`NUM` AS `NUM`,
        `member`.`gamedata_ps_genre`.`TITLE` AS `TITLE`,
        `member`.`gamedata_ps_genre`.`genre` AS `GENRE`
    FROM
        `member`.`gamedata_ps_genre`
    WHERE
        (`member`.`gamedata_ps_genre`.`genre` IN ('액션' , 'RPG',
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
                IFNULL(MAX(`member`.`gamedata_ps_genre`.`NUM`), 0)
            FROM
                `member`.`gamedata_ps_genre`) + `member`.`gamedata_switch_genre`.`NUM`) AS `NUM`,
        `member`.`gamedata_switch_genre`.`TITLE` AS `TITLE`,
        `member`.`gamedata_switch_genre`.`GENRE` AS `GENRE`
    FROM
        `member`.`gamedata_switch_genre`
    WHERE
        (`member`.`gamedata_switch_genre`.`GENRE` IN ('액션' , 'RPG',
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