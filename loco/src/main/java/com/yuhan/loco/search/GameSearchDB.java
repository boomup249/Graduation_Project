package com.yuhan.loco.search;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="research_view")
public class GameSearchDB {
	private String NUM;
	private String TITLE;
	private String PRICE;
	private String SALEPRICE;
	private String SALEPER;
	@Id
	private String IMGDATA;
	private String SITEAVAILABILITY;
	public String getNUM() {
		return NUM;
	}
	public void setNUM(String nUM) {
		NUM = nUM;
	}
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	public String getSALEPRICE() {
		return SALEPRICE;
	}
	public void setSALEPRICE(String sALEPRICE) {
		SALEPRICE = sALEPRICE;
	}
	public String getIMGDATA() {
		return IMGDATA;
	}
	public void setIMGDATA(String iMGDATA) {
		IMGDATA = iMGDATA;
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
	public String getSALEPER() {
		return SALEPER;
	}
	public void setSALEPER(String sALEPER) {
		SALEPER = sALEPER;
	}
	@Override
	public String toString() {
		return "GameSearchDB [NUM=" + NUM + ", TITLE=" + TITLE + ", PRICE=" + PRICE + ", SALEPRICE=" + SALEPRICE
				+ ", SALEPER=" + SALEPER + ", IMGDATA=" + IMGDATA + ", SITEAVAILABILITY=" + SITEAVAILABILITY + "]";
	}
	
}
