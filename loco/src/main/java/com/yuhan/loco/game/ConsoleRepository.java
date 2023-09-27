package com.yuhan.loco.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsoleRepository extends JpaRepository<ConsoleDB, Long> {
	//List<ConsoleDB> findBySiteavailabilty(String siteavailabilty); //테스트용
	Page<ConsoleDB> findAll(Pageable pageable); //다 받아오기
	
}
