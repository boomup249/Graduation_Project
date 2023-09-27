package com.yuhan.loco.bbs;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BBSRepository extends JpaRepository<BBSDTO, Long> {
    // 게시물 제목으로 게시물을 검색하는 메서드 추가
    List<BBSDTO> findByTitle(String title);
    List<BBSDTO> findByTitleContaining(String keyword);
    List<BBSDTO> findByAuthor(String author);
    List<BBSDTO> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<BBSDTO> findByTitleAndAuthor(String title, String author);
    
}
