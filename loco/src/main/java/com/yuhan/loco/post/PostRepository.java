package com.yuhan.loco.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuhan.loco.bbs.BBSDB;


@Repository
public interface PostRepository extends JpaRepository<PostDB, String> {
	PostDB findById(Long id);

}
