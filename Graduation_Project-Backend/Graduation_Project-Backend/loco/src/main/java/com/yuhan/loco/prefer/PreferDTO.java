package com.yuhan.loco.prefer;




import jakarta.validation.constraints.Email;


public class PreferDTO {
	//
	@Email
	private String userLike;
	private String userHate;
	
	

	//getter setter
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

}