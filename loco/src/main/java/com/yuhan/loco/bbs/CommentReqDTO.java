package com.yuhan.loco.bbs;
import lombok.Builder;
import lombok.Data;
@Data
@Builder

public class CommentReqDTO {

    private Long id;
	private BBSDB bbs;
    private String writer; // 작성자
    private String comment;

    public CommentDB toEntity() {
    	CommentDB comments = CommentDB.builder()
    			.id(id)
    			.bbs(bbs)
    			.comment(comment)
    			.writer(writer)
    			.build();
    	return comments;
    }

    // Getter for id

    }
