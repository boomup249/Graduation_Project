package com.yuhan.loco.game;

import java.sql.Date;

import com.yuhan.loco.prefer.PreferDB;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "gamedata_steam")
public class GameDB {
	@Id

	private int NUM;
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
