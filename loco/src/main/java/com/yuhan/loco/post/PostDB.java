package com.yuhan.loco.post;

import java.util.List;

import com.yuhan.loco.bbs.BBSDB;
import com.yuhan.loco.bbs.CommentDB;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "bbs_content")
public class PostDB {
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
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<CommentDB> commentDB;


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
	public BBSDB getBbs() {
		return bbsDB;
	}
	public void setBbs(BBSDB bbsDB) {
		this.bbsDB = bbsDB;
	}
	public List<CommentDB> getCommentDB() {
		return commentDB;
	}
	public void setCommentDB(List<CommentDB> commentDB) {
		this.commentDB = commentDB;
	}


}
