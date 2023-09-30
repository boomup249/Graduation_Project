package com.yuhan.loco.bbs;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostDB, String> {
    
    
}
