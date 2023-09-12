package com.yuhan.loco.game;


public class GameDTO {
	private int ranking;
	private String gameName;
	private String gameUrl;
	
	private String price;
	private String DcPrice;
	private String DcRate;
	
	
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
	
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDcPrice() {
		return DcPrice;
	}
	public void setDcPrice(String dcPrice) {
		DcPrice = dcPrice;
	}
	public String getDcRate() {
		return DcRate;
	}
	public void setDcRate(String dcRate) {
		DcRate = dcRate;
	}
	
	
	//생성자
	public GameDTO(int ranking, String gameName, String price, String dcPrice, String dcRate, String gameUrl) {
		super();
		this.ranking = ranking;
		this.gameName = gameName;
		this.gameUrl = gameUrl;
		this.price = price;
		DcPrice = dcPrice;
		DcRate = dcRate;
	}
	
	
}
