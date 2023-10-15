package com.yuhan.loco.news;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "release_info")
public class NewsDB {
	
	private Date DATE;
	
	@Id
	private String TITLE;
	
	private String PLATFORM;
	private String PRICE;
	private String ETC;
	private String VARIA;
	
	//getter setter
	public Date getDATE() {
		return DATE;
	}
	public void setDATE(Date dATE) {
		DATE = dATE;
	}
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	public String getPLATFORM() {
		return PLATFORM;
	}
	public void setPLATFORM(String pLATFORM) {
		PLATFORM = pLATFORM;
	}
	public String getPRICE() {
		return PRICE;
	}
	public void setPRICE(String pRICE) {
		PRICE = pRICE;
	}
	public String getETC() {
		return ETC;
	}
	public void setETC(String eTC) {
		ETC = eTC;
	}
	public String getVARIA() {
		return VARIA;
	}
	public void setVARIA(String vARIA) {
		VARIA = vARIA;
	}
	//
	
	
}
