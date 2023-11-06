CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `research_view` AS
    SELECT 
        `gamedata_ps`.`NUM` AS `NUM`,
        `gamedata_ps`.`TITLE` AS `TITLE`,
        `gamedata_ps`.`PRICE` AS `PRICE`,
        `gamedata_ps`.`SALEPER` AS `SALEPER`,
        `gamedata_ps`.`SALEPRICE` AS `SALEPRICE`,
        `gamedata_ps`.`IMGDATA` AS `IMGDATA`,
        (CASE
            WHEN (`gamedata_ps`.`TITLE` IS NOT NULL) THEN 'Ps'
            ELSE 'None'
        END) AS `SITEAVAILABILITY`
    FROM
        `gamedata_ps` 
    UNION SELECT 
        ((SELECT 
                IFNULL(MAX(`gamedata_ps`.`NUM`), 0)
            FROM
                `gamedata_ps`) + `gamedata_switch`.`NUM`) AS `NUM`,
        `gamedata_switch`.`TITLE` AS `TITLE`,
        `gamedata_switch`.`PRICE` AS `PRICE`,
        `gamedata_switch`.`SALEPER` AS `SALEPER`,
        `gamedata_switch`.`SALEPRICE` AS `SALEPRICE`,
        `gamedata_switch`.`IMGDATA` AS `IMGDATA`,
        (CASE
            WHEN (`gamedata_switch`.`TITLE` IS NOT NULL) THEN 'Switch'
            ELSE 'None'
        END) AS `SITEAVAILABILITY`
    FROM
        `gamedata_switch` 
    UNION SELECT 
        CONCAT(`gamedata_steam`.`NUM`, '_') AS `NUM`,
        `gamedata_steam`.`TITLE` AS `TITLE`,
        `gamedata_steam`.`PRICE` AS `PRICE`,
        `gamedata_steam`.`SALEPER` AS `SALEPER`,
        `gamedata_steam`.`SALEPRICE` AS `SALEPRICE`,
        `gamedata_steam`.`IMGDATA` AS `IMGDATA`,
        (CASE
            WHEN (`gamedata_steam`.`TITLE` IS NOT NULL) THEN 'Steam'
            ELSE 'None'
        END) AS `SITEAVAILABILITY`
    FROM
        `gamedata_steam` 
    UNION SELECT 
        CONCAT('_', `gamedata_epic`.`NUM`) AS `NUM`,
        `gamedata_epic`.`TITLE` AS `TITLE`,
        `gamedata_epic`.`PRICE` AS `PRICE`,
        `gamedata_epic`.`SALEPER` AS `SALEPER`,
        `gamedata_epic`.`SALEPRICE` AS `SALEPRICE`,
        `gamedata_epic`.`IMGDATA` AS `IMGDATA`,
        (CASE
            WHEN (`gamedata_epic`.`TITLE` IS NOT NULL) THEN 'Epic'
            ELSE 'None'
        END) AS `SITEAVAILABILITY`
    FROM
        `gamedata_epic`