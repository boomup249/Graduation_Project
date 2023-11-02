package com.yuhan.loco.post;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.yuhan.loco.bbs.BBSDB;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
public class PostResDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long bbs_id;
    private String category;
	private String title;
    private String writer; // 작성자
    private String content;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Long getBbs_id() {
		return bbs_id;
	}
	public void setBbs_id(Long bbs_id) {
		this.bbs_id = bbs_id;
	}
	
	
	public PostResDTO(PostDB postDB) {
		this.id = postDB.getId();
		this.category = postDB.getCategory();
		this.title = postDB.getTitle();
		this.writer = postDB.getWriter();
		this.content = postDB.getContent();
		//this.bbs_id = postDB.getBbs().getId();
		
	}
}
