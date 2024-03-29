package com.yuhan.loco.game.genre;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Immutable
@Table(name = "console_game_genre")
public class ConsoleGenreDB {
	@Id
	private Integer NUM;

	private String TITLE;
	private String GENRE;

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
	public String getGENRE() {
		return GENRE;
	}
	public void setGENRE(String gENRE) {
		GENRE = gENRE;
	}


}
