package com.yuhan.loco.myapp;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PostRepository extends JpaRepository<Post, Long> {
    // 게시물 제목으로 게시물을 검색하는 메서드 추가
    List<Post> findByTitle(String title);
    List<Post> findByContent(String content);
    List<Post> findByTitleAndContent(String title, String content);
    List<Post> findByTitleContaining(String keyword);
    List<Post> findByAuthor(String author);
    List<Post> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Post> findByTitleAndAuthor(String title, String author);
    
}
