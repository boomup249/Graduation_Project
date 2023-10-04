package com.yuhan.loco.game.genre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PcGenreRepository extends JpaRepository<PcGenreDB, Long>{
	PcGenreDB findByNUM(Integer NUM); //test
	
	//장르를 인수로 넣으면, 해당 장르인 게임 타이틀 목록으로 반환
	@Query("SELECT DISTINCT p.TITLE FROM PcGenreDB p WHERE (p.GENRE IN :genreList)")
	List<String> findTitlesByGenre(@Param("genreList") List<String> genreList);
}