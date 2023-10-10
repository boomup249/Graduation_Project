package com.yuhan.loco.game;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsoleRepository extends JpaRepository<ConsoleDB, Long> {
	List<ConsoleDB> findBySITEAVAILABILITY(String siteAvailabilty);
	
	Page<ConsoleDB> findAll(Pageable pageable); //다 받아오기
	ConsoleDB findByNUM(Integer num);
	
	//정렬 및 필터 조건에 맞춰 받아오기: 카테고리 not 전체
	@Query("SELECT c FROM ConsoleDB c WHERE (c.SITEAVAILABILITY IN :siteAvailabilityList) AND (c.TITLE IN :titleList)")
	Page<ConsoleDB> findByCriteria(
			@Param("siteAvailabilityList") List<String> siteAvailabilityList,
			@Param("titleList") List<String> titleList,
			Pageable pageable);
		
	//정렬 및 필터 조건에 맞춰 받아오기: 카테고리 = 전체
	@Query("SELECT c FROM ConsoleDB c WHERE (c.SITEAVAILABILITY IN :siteAvailabilityList)")
	Page<ConsoleDB> findByCriteriaOnlySite(
			@Param("siteAvailabilityList") List<String> siteAvailabilityList,
			Pageable pageable);
}
