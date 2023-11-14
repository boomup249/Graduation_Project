package com.yuhan.loco.profile;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "profile_data")
public class profileDB {
	@Id
	private String ID;
	private String NICKNAME;
	private String DESCRIPTION;
    @Lob
    @Basic(fetch = FetchType.LAZY)
	private byte[] IMG;

	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getNICKNAME() {
		return NICKNAME;
	}
	public void setNICKNAME(String nICKNAME) {
		NICKNAME = nICKNAME;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public byte[] getIMG() {
		return IMG;
	}
	public void setIMG(byte[] iMG) {
		IMG = iMG;
	}








}
