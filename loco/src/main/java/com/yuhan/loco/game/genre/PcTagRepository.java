package com.yuhan.loco.game.genre;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//pcTag view는 모든 장르를 가져오는 뷰임.

@Repository
public interface PcTagRepository extends JpaRepository<PcTagDB, Long>{

	/*genre 반환*/
	//타이틀 넣어주면, 해당 타이틀인 장르 모두 반환
	@Query("SELECT DISTINCT p.GENRE FROM PcTagDB p WHERE p.TITLE = :title")
	List<String> findGenresByTitle(@Param("title") String title);

}
