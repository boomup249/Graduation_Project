package com.yuhan.loco.user;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "test")
public class UserDB {
	@Id
	private String ID;
	
	private String PWD;
	
	private Date BIRTH;
	private String GENDER;
	
	/*
	private String userLike;
	private String userHate;
	*/
	
	//getter setter
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPWD() {
		return PWD;
	}
	public void setPWD(String pWD) {
		PWD = pWD;
	}
	public Date getBIRTH() {
		return BIRTH;
	}
	public void setBIRTH(Date bIRTH) {
		BIRTH = bIRTH;
	}
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}
	
	/*
	public String getUserLike() {
		return userLike;
	}
	public void setUserLike(String userLike) {
		this.userLike = userLike;
	}
	public String getUserHate() {
		return userHate;
	}
	public void setUserHate(String userHate) {
		this.userHate = userHate;
	}
	*/
	
}
