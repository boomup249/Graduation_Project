package com.yuhan.loco.game;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Immutable
@Table(name = "game_switch_ps")
public class ConsoleDB {
	@Id
	private Integer SWITCHNUM;
	private Integer PSNUM;
	private String TITLE;
	private String SITEAVAILABILIT;

	private String SWITCHPRICE;
	private String PSPRICE;

	private String SWITCHSALEPRICE;
	private String PSSALEPRICE;

	private String SWITCHSALEPER;
	private String PSSALEPER;

	private String SWITCHDESCRIPTION;
	private String PSDESCRIPTION;

	private String SWITCHIMGDATA;
	private String PSIMGDATA;

	private String SWITCHGAMEIMG;
	private String PSGAMEIMG;

	private String SWITCHURL;
	private String PSURL;
	public Integer getSWITCHNUM() {
		return SWITCHNUM;
	}
	public void setSWITCHNUM(Integer sWITCHNUM) {
		SWITCHNUM = sWITCHNUM;
	}
	public Integer getPSNUM() {
		return PSNUM;
	}
	public void setPSNUM(Integer pSNUM) {
		PSNUM = pSNUM;
	}
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	public String getSITEAVAILABILIT() {
		return SITEAVAILABILIT;
	}
	public void setSITEAVAILABILIT(String sITEAVAILABILIT) {
		SITEAVAILABILIT = sITEAVAILABILIT;
	}
	public String getSWITCHPRICE() {
		return SWITCHPRICE;
	}
	public void setSWITCHPRICE(String sWITCHPRICE) {
		SWITCHPRICE = sWITCHPRICE;
	}
	public String getPSPRICE() {
		return PSPRICE;
	}
	public void setPSPRICE(String pSPRICE) {
		PSPRICE = pSPRICE;
	}
	public String getSWITCHSALEPRICE() {
		return SWITCHSALEPRICE;
	}
	public void setSWITCHSALEPRICE(String sWITCHSALEPRICE) {
		SWITCHSALEPRICE = sWITCHSALEPRICE;
	}
	public String getPSSALEPRICE() {
		return PSSALEPRICE;
	}
	public void setPSSALEPRICE(String pSSALEPRICE) {
		PSSALEPRICE = pSSALEPRICE;
	}
	public String getSWITCHSALEPER() {
		return SWITCHSALEPER;
	}
	public void setSWITCHSALEPER(String sWITCHSALEPER) {
		SWITCHSALEPER = sWITCHSALEPER;
	}
	public String getPSSALEPER() {
		return PSSALEPER;
	}
	public void setPSSALEPER(String pSSALEPER) {
		PSSALEPER = pSSALEPER;
	}
	public String getSWITCHDESCRIPTION() {
		return SWITCHDESCRIPTION;
	}
	public void setSWITCHDESCRIPTION(String sWITCHDESCRIPTION) {
		SWITCHDESCRIPTION = sWITCHDESCRIPTION;
	}
	public String getPSDESCRIPTION() {
		return PSDESCRIPTION;
	}
	public void setPSDESCRIPTION(String pSDESCRIPTION) {
		PSDESCRIPTION = pSDESCRIPTION;
	}
	public String getSWITCHIMGDATA() {
		return SWITCHIMGDATA;
	}
	public void setSWITCHIMGDATA(String sWITCHIMGDATA) {
		SWITCHIMGDATA = sWITCHIMGDATA;
	}
	public String getPSIMGDATA() {
		return PSIMGDATA;
	}
	public void setPSIMGDATA(String pSIMGDATA) {
		PSIMGDATA = pSIMGDATA;
	}
	public String getSWITCHGAMEIMG() {
		return SWITCHGAMEIMG;
	}
	public void setSWITCHGAMEIMG(String sWITCHGAMEIMG) {
		SWITCHGAMEIMG = sWITCHGAMEIMG;
	}
	public String getPSGAMEIMG() {
		return PSGAMEIMG;
	}
	public void setPSGAMEIMG(String pSGAMEIMG) {
		PSGAMEIMG = pSGAMEIMG;
	}
	public String getSWITCHURL() {
		return SWITCHURL;
	}
	public void setSWITCHURL(String sWITCHURL) {
		SWITCHURL = sWITCHURL;
	}
	public String getPSURL() {
		return PSURL;
	}
	public void setPSURL(String pSURL) {
		PSURL = pSURL;
	}







}
