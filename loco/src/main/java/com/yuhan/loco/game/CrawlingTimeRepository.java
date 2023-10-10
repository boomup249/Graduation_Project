package com.yuhan.loco.game;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlingTimeRepository extends JpaRepository<CrawlingTimeDB, LocalDateTime> {
	List<CrawlingTimeDB> findAll();
}
