package com.yuhan.loco.game;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;


public class GameDTO {
	//
	@Id
	private int seq;
	
	@Size(max = 45)
	private String gameName;
	
	private String gameUrl;

	
	//getter setter
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameUrl() {
		return gameUrl;
	}

	public void setGameUrl(String gameUrl) {
		this.gameUrl = gameUrl;
	}
	
	
}
