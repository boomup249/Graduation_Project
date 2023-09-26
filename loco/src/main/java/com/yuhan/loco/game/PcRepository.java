package com.yuhan.loco.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PcRepository extends JpaRepository<PcDB, Long>{
	List<PcDB> findBySITEAVAILABILITY(String SITEAVAILABILITY); //테스트용
	
	Page<PcDB> findAll(Pageable pageable); //다 받아오기
}
