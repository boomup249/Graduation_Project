package com.yuhan.loco.game;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Immutable
@Table(name = "game_steam_epic")
public class PcDB {
	@Id
	private String KEY;
	private Integer STEAMNUM;
	private Integer EPICNUM;
	private String TITLE;
	private String SITEAVAILABILITY;

	private String STEAMPRICE;
	private String EPICPRICE;

	private String STEAMSALEPRICE;
	private String EPICSALEPRICE;

	private String STEAMSALEPER;
	private String EPICSALEPER;

	private String STEAMDESCRIPTION;
	private String EPICDESCRIPTION;

	private String STEAMIMGDATA;
	private String EPICIMGDATA;

	private String STEAMGAMEIMG;
	private String EPICGAMEIMG;

	private String STEAMURL;
	private String EPICURL;


	//getter setter
	public String getKEY() {
		return KEY;
	}
	public void setKEY(String kEY) {
		KEY = kEY;
	}
	public Integer getSTEAMNUM() {
		return STEAMNUM;
	}
	public void setSTEAMNUM(Integer sTEAMNUM) {
		STEAMNUM = sTEAMNUM;
	}
	public Integer getEPICNUM() {
		return EPICNUM;
	}
	public void setEPICNUM(Integer ePICNUM) {
		EPICNUM = ePICNUM;
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
	public String getSTEAMPRICE() {
		return STEAMPRICE;
	}
	public void setSTEAMPRICE(String sTEAMPRICE) {
		STEAMPRICE = sTEAMPRICE;
	}
	public String getEPICPRICE() {
		return EPICPRICE;
	}
	public void setEPICPRICE(String ePICPRICE) {
		EPICPRICE = ePICPRICE;
	}
	public String getSTEAMSALEPRICE() {
		return STEAMSALEPRICE;
	}
	public void setSTEAMSALEPRICE(String sTEAMSALEPRICE) {
		STEAMSALEPRICE = sTEAMSALEPRICE;
	}
	public String getEPICSALEPRICE() {
		return EPICSALEPRICE;
	}
	public void setEPICSALEPRICE(String ePICSALEPRICE) {
		EPICSALEPRICE = ePICSALEPRICE;
	}
	public String getSTEAMSALEPER() {
		return STEAMSALEPER;
	}
	public void setSTEAMSALEPER(String sTEAMSALEPER) {
		STEAMSALEPER = sTEAMSALEPER;
	}
	public String getEPICSALEPER() {
		return EPICSALEPER;
	}
	public void setEPICSALEPER(String ePICSALEPER) {
		EPICSALEPER = ePICSALEPER;
	}
	public String getSTEAMDESCRIPTION() {
		return STEAMDESCRIPTION;
	}
	public void setSTEAMDESCRIPTION(String sTEAMDESCRIPTION) {
		STEAMDESCRIPTION = sTEAMDESCRIPTION;
	}
	public String getEPICDESCRIPTION() {
		return EPICDESCRIPTION;
	}
	public void setEPICDESCRIPTION(String ePICDESCRIPTION) {
		EPICDESCRIPTION = ePICDESCRIPTION;
	}
	public String getSTEAMIMGDATA() {
		return STEAMIMGDATA;
	}
	public void setSTEAMIMGDATA(String sTEAMIMGDATA) {
		STEAMIMGDATA = sTEAMIMGDATA;
	}
	public String getEPICIMGDATA() {
		return EPICIMGDATA;
	}
	public void setEPICIMGDATA(String ePICIMGDATA) {
		EPICIMGDATA = ePICIMGDATA;
	}
	public String getSTEAMGAMEIMG() {
		return STEAMGAMEIMG;
	}
	public void setSTEAMGAMEIMG(String sTEAMGAMEIMG) {
		STEAMGAMEIMG = sTEAMGAMEIMG;
	}
	public String getEPICGAMEIMG() {
		return EPICGAMEIMG;
	}
	public void setEPICGAMEIMG(String ePICGAMEIMG) {
		EPICGAMEIMG = ePICGAMEIMG;
	}
	public String getSTEAMURL() {
		return STEAMURL;
	}
	public void setSTEAMURL(String sTEAMURL) {
		STEAMURL = sTEAMURL;
	}
	public String getEPICURL() {
		return EPICURL;
	}
	public void setEPICURL(String ePICURL) {
		EPICURL = ePICURL;
	}


}
