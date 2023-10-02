package com.yuhan.loco.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsoleRepository extends JpaRepository<ConsoleDB, Long> {
	//List<ConsoleDB> findBySiteavailabilty(String siteavailabilty); //테스트용
	@Override
	Page<ConsoleDB> findAll(Pageable pageable); //다 받아오기

}
