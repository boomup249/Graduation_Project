package com.yuhan.loco.game;


import java.util.List;


public class ConsoleDTO {
	private int NUM;
	//dto 리스트
	private List<ConsoleDTO> ConsoleDTO;

	public int getNUM() {
		return NUM;
	}
	public void setNUM(int nUM) {
		NUM = nUM;
	}
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	public String getPRICE() {
		return PRICE;
	}
	public void setPRICE(String pRICE) {
		PRICE = pRICE;
	}
	public String getSALEPRICE() {
		return SALEPRICE;
	}
	public void setSALEPRICE(String sALEPRICE) {
		SALEPRICE = sALEPRICE;
	}
	public String getSALEPER() {
		return SALEPER;
	}
	public void setSALEPER(String sALEPER) {
		SALEPER = sALEPER;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public String getIMGDATA() {
		return IMGDATA;
	}
	public void setIMGDATA(String iMGDATA) {
		IMGDATA = iMGDATA;
	}
	public String getGAMEIMG() {
		return GAMEIMG;
	}
	public void setGAMEIMG(String gAMEIMG) {
		GAMEIMG = gAMEIMG;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	private String TITLE;
	private String PRICE;
	private String SALEPRICE;
	private String SALEPER;
	private String DESCRIPTION;
	private String IMGDATA;
	private String GAMEIMG;
	private String URL;


}
