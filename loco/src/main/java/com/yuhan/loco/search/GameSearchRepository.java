package com.yuhan.loco.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSearchRepository extends JpaRepository<GameSearchDB, String> {
	@Query(value = "SELECT view.NUM, view.TITLE, view.PRICE,view.SALEPER, view.SALEPRICE, view.IMGDATA, view.SITEAVAILABILITY FROM research_view AS view WHERE view.TITLE REGEXP :title LIMIT 10", nativeQuery = true)
	List<GameSearchDB> findByTITLEContaining(@Param("title") String title);
	
	@Query(value = "SELECT v.NUM, v.TITLE, v.PRICE, v.SALEPRICE, v.SALEPER, v.IMGDATA, v.SITEAVAILABILITY FROM research_view AS v WHERE v.TITLE REGEXP :title", nativeQuery = true)
	Page<GameSearchDB> findByTITLE(@Param("title") String title, Pageable pageable);
}
