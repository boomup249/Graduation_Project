package com.yuhan.loco.bbs;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bbs")
public class BBSDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	private String title;
    private String writer; // 작성자
    private String category;
    private LocalDate date; // 날짜
    private int views; // 조회수
    private int comments; // 댓글 수
    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="number")
    private PostDB postDB;*/
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
    
    // Getter for id
    
    }
