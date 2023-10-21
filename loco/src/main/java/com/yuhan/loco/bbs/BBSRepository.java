package com.yuhan.loco.bbs;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yuhan.loco.post.PostDB;



@Repository
public interface BBSRepository extends JpaRepository<BBSDB, String> {
    // 게시물 제목으로 게시물을 검색하는 메서드 추가
    /*List<BBSDTO> findByTitle(String title);
    List<BBSDTO> findByTitleContaining(String keyword);
    List<BBSDTO> findByAuthor(String author);
    List<BBSDTO> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<BBSDTO> findByTitleAndAuthor(String title, String author);
    */
	List<BBSDB> findAll();
	Page<BBSDB> findAll(Pageable pageable);
	BBSDB findById(Long id);
	@Modifying
    @Query(value = "update bbs BBS set BBS.views = BBS.views + 1 where BBS.id = :id", nativeQuery=true)
    int updateView(@Param("id") Long id);
	
	Page<BBSDB> findByTitleContaining(String title, Pageable pageable);
	Page<BBSDB> findByCategory(String category, Pageable pageable);
}
