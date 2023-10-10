package com.yuhan.loco.game.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

//consoleTag view는 모든 장르를 가져오는 뷰임.

@Repository
public interface ConsoleTagRepository extends JpaRepository<ConsoleTagDB, Long>{
	
	/*genre 반환*/
	//타이틀 넣어주면, 해당 타이틀인 장르 모두 반환
	@Query("SELECT DISTINCT c.GENRE FROM ConsoleTagDB c WHERE c.TITLE = :title")
	List<String> findGenresByTitle(@Param("title") String title);
}
