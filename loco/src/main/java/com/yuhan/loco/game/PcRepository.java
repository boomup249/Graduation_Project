package com.yuhan.loco.game;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PcRepository extends JpaRepository<PcDB, String>{
	List<PcDB> findBySITEAVAILABILITY(String SITEAVAILABILITY); //테스트용

	@Override
	Page<PcDB> findAll(Pageable pageable); //다 받아오기
	PcDB findByKEY(String key); // key에 해당하는 PC 게임 정보 가져오기

	//정렬 및 필터 조건에 맞춰 받아오기: 카테고리 not 전체
	@Query("SELECT p FROM PcDB p WHERE (p.SITEAVAILABILITY IN :siteAvailabilityList) AND (p.TITLE IN :titleList)")
	Page<PcDB> findByCriteria(
			@Param("siteAvailabilityList") List<String> siteAvailabilityList,
			@Param("titleList") List<String> titleList,
			Pageable pageable);

	//정렬 및 필터 조건에 맞춰 받아오기: 카테고리 = 전체
	@Query("SELECT p FROM PcDB p WHERE (p.SITEAVAILABILITY IN :siteAvailabilityList)")
	Page<PcDB> findByCriteriaOnlySite(
			@Param("siteAvailabilityList") List<String> siteAvailabilityList,
			Pageable pageable);
}
