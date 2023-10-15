package com.yuhan.loco.news;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsDB, String> {
	@Query("select n from NewsDB n where n.ETC IS NULL")
	List<NewsDB> findEtcIsNull();
}
