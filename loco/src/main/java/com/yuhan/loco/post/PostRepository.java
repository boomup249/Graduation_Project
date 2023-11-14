package com.yuhan.loco.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<PostDB, Long> {
	PostDB findByBbs(Long id);
}
