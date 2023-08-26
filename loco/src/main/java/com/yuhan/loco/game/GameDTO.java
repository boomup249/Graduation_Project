package com.yuhan.loco.game;


public class GameDTO {
	private int ranking;
	private String gameName;
	private String gameUrl;
	
	//getter setter
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
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
	
	//생성자
	public GameDTO(int ranking, String gameName, String gameUrl) {
		super();
		this.ranking = ranking;
		this.gameName = gameName;
		this.gameUrl = gameUrl;
	}
	
}
