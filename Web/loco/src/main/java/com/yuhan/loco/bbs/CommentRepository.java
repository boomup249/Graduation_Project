package com.yuhan.loco.bbs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentDB, Long> {
	List<CommentDB> findByPost(Long id);
	@Modifying
    @Query(value = "update bbs BBS set BBS.comment = BBS.comment + 1 where BBS.id = :id", nativeQuery=true)
    int updateComment(@Param("id") Long id);
}
