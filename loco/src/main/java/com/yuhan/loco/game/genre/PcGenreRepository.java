package com.yuhan.loco.game.genre;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//pcGenre view는 지정된 15개 장르만 가져온 뷰임.
//이 뷰에 존재하지 않으면 장르 = 기타임.

@Repository
public interface PcGenreRepository extends JpaRepository<PcGenreDB, Long>{
	PcGenreDB findByNUM(Integer NUM); //test

	/*title 반환*/
	//장르 리스트를 인수로 넣으면, 해당 장르인 게임 타이틀 목록으로 반환
	@Query("SELECT DISTINCT p.TITLE FROM PcGenreDB p WHERE (p.GENRE IN :genreList)")
	List<String> findTitlesByGenre(@Param("genreList") List<String> genreList);

	//모든 타이틀 반환
	@Query("SELECT DISTINCT p.TITLE FROM PcGenreDB p")
	List<String> findTitles();

	/*genre 반환*/
	//타이틀 넣어주면, 해당 타이틀인 장르 모두 반환
	@Query("SELECT DISTINCT p.GENRE FROM PcGenreDB p WHERE p.TITLE = :title")
	List<String> findGenresByTitle(@Param("title") String title);
}