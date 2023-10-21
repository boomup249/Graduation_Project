package com.yuhan.loco.bbs;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
@Builder
@Entity
@Table(name = "bbs_comment")
public class CommentDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private BBSDB bbs;
	//private Long post_id;
    private String writer; // 작성자
    private String comment;
    
    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="number")
    private PostDB postDB;*/

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BBSDB getBbs() {
		return bbs;
	}
	public void setBbs(BBSDB bbs) {
		this.bbs = bbs;
	}
	/*public Long getPost_id() {
		return post_id;
	}
	public void setPost_id(Long post_id) {
		this.post_id = post_id;
	}*/
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}


    // Getter for id

    }
