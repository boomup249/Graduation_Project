package com.yuhan.loco.bbs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentDB, Long> {
	List<CommentDB> findByPost(Long id);
}
