package com.yuhan.loco.game;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ConsoleRepository extends JpaRepository<ConsoleDB, Long> {
	List<ConsoleDB> findBySITEAVAILABILITY(String SITEAVAILABILITY); //테스트용
	Page<ConsoleDB> findAll(Pageable pageable); //다 받아오기
}
