package com.yuhan.loco.game;


import java.sql.Date;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> 157d06568a90830c2c1242ee8775f96b0e4e35a9

import jakarta.validation.constraints.Email;


public class GameDTO {
<<<<<<< HEAD
	private int NUM;
=======
	
    public String getSITEAVAILABILITY() {
        return SITEAVAILABILITY;
    }

    public void setSITEAVAILABILITY(String SITEAVAILABILITY) {
        this.SITEAVAILABILITY = SITEAVAILABILITY;
    }
    
    private int NUM;
>>>>>>> 157d06568a90830c2c1242ee8775f96b0e4e35a9
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
<<<<<<< HEAD
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
=======
	}	
	
	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}


	private String SITEAVAILABILITY;
>>>>>>> 157d06568a90830c2c1242ee8775f96b0e4e35a9
	private String TITLE;
	private String PRICE;
	private String SALEPRICE;
	private String SALEPER;
	private String DESCRIPTION;
	private String IMGDATA;
	private String GAMEIMG;
	private String URL;
	

}
