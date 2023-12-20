package com.yuhan.loco.prefer;


import com.yuhan.loco.user.UserDB;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prefer")
public class PreferDB {

	//장르
	@Id
	private String ID;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private UserDB userDB;
	private String love;
	private String dislike;

	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public UserDB getUserDB() {
		return userDB;
	}
	public void setUserDB(UserDB userDB) {

		this.userDB = userDB;
		this.ID = userDB.getID();
	}

	public String getLove() {
		return love;
	}
	public void setLove(String love) {
		this.love = love;
	}
	public String getDislike() {
		return dislike;
	}
	public void setDislike(String dislike) {
		this.dislike = dislike;
	}






}
