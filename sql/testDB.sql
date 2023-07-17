USE member;

create table test (
	id VARCHAR(100) NOT NULL,
	pwd VARCHAR(16) NOT NULL,
	birth DATE,
	gender VARCHAR(3),

	PRIMARY KEY(id)
)