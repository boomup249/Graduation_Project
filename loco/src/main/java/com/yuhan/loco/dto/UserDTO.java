package com.yuhan.loco.dto;

import java.sql.Date;

public class UserDTO {
	//
	private String userEmail;
	private String userPwd;
	private String userPwdck;
	
	private String userGender;
	private Date userBirth;
	
	private String userLike;
	private String userHate;
	
	//getter setter
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserPwdck() {
		return userPwdck;
	}
	public void setUserPwdck(String userPwdck) {
		this.userPwdck = userPwdck;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public Date getUserBirth() {
		return userBirth;
	}
	public void setUserBirth(Date userBirth) {
		this.userBirth = userBirth;
	}
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
	
	//toString
	@Override
	public String toString() {
		return "UserDTO [userEmail=" + userEmail + ", userPwd=" + userPwd + ", userGender="
				+ userGender + ", userBirth=" + userBirth + ", userLike=" + userLike + ", userHate=" + userHate + "]";
	}
	
	
	
	
	
	

}
