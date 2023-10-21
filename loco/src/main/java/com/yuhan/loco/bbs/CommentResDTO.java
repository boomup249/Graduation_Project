package com.yuhan.loco.bbs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResDTO {
	private Long id;
	private Long post_id;
	private String comment;
	private String writer;
	
	public CommentResDTO(CommentDB comment) {
		this.id = comment.getId();
		this.post_id = comment.getBbs().getId();
		this.comment = comment.getComment();
		this.writer = comment.getWriter();
	}
}
