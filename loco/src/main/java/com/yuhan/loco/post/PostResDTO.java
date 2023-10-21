package com.yuhan.loco.post;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.yuhan.loco.bbs.BBSDB;
import com.yuhan.loco.bbs.CommentResDTO;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
public class PostResDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bbs_id")
	private BBSDB bbsDB;
    private String category;
	private String title;
    private String writer; // 작성자
    private String content;
    private Long post_id;
    private List<CommentResDTO> comments;


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
	public Long getPost_id() {
		return post_id;
	}
	public void setPost_id(Long post_id) {
		this.post_id = post_id;
	}
	public List<CommentResDTO> getComments() {
		return comments;
	}
	public void setComments(List<CommentResDTO> comments) {
		this.comments = comments;
	}

	
	
	public BBSDB getBbsDB() {
		return bbsDB;
	}
	public void setBbsDB(BBSDB bbsDB) {
		this.bbsDB = bbsDB;
	}
	public PostResDTO(PostDB postDB) {
		this.id = postDB.getId();
		this.category = postDB.getCategory();
		this.title = postDB.getTitle();
		this.writer = postDB.getWriter();
		this.content = postDB.getContent();
		this.post_id = postDB.getBbs().getId();
		this.comments = postDB.getCommentDB().stream().map(CommentResDTO::new).collect(Collectors.toList());
		
	}
}
