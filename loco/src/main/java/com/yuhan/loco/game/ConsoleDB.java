package com.yuhan.loco.game;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Immutable
@Table(name = "game_switch_ps2")
public class ConsoleDB {
	@Id
	private Integer NUM;
	
	private String TITLE;
	private String SITEAVAILABILITY;
	
	private String PRICE;
	private String SALEPRICE;
	private String SALEPER;
	
	private String DESCRIPTION;
	private String IMGDATA;
	private String GAMEIMG;
	private String URL;
	
	//getter setter
	public Integer getNUM() {
		return NUM;
	}
	public void setNUM(Integer nUM) {
		NUM = nUM;
	}
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	public String getSITEAVAILABILITY() {
		return SITEAVAILABILITY;
	}
	public void setSITEAVAILABILITY(String sITEAVAILABILITY) {
		SITEAVAILABILITY = sITEAVAILABILITY;
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
	
	
}
