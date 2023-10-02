package com.yuhan.loco.bbs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BBSRepository extends JpaRepository<BBSDB, String> {
    // 게시물 제목으로 게시물을 검색하는 메서드 추가
    /*List<BBSDTO> findByTitle(String title);
    List<BBSDTO> findByTitleContaining(String keyword);
    List<BBSDTO> findByAuthor(String author);
    List<BBSDTO> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<BBSDTO> findByTitleAndAuthor(String title, String author);
    @Modifying
    @Query("update bbs BBS set BBS.views = BBS.views + 1 where BBS.id = :id")
    int updateView(Long id);*/

}
