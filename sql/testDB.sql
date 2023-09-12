USE member;

create table test (
	email VARCHAR(255) NOT NULL,
    id VARCHAR(255),
	pwd VARCHAR(16) NOT NULL,
	birth DATE,
	gender VARCHAR(3),
    
    `action` TINYINT,
    action_adventure TINYINT,
    survival TINYINT,
    shooting TINYINT,
    FPS TINYINT,
    RPG TINYINT,
    ARPG TINYINT,
    MMORPG TINYINT,
    open_world TINYINT,
    hack_and_slash TINYINT,
    adventure TINYINT,
    sports TINYINT,
    racing TINYINT,
    casual TINYINT,
    puzzle TINYINT,
    
	PRIMARY KEY(email)
)