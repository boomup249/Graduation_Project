package com.yuhan.loco.game;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Immutable
@Table(name = "crawling_time")
public class CrawlingTimeDB {
	@Id
	private LocalDateTime ENDTIME;

	
	//getter setter
	public LocalDateTime getENDTIME() {
		return ENDTIME;
	}

	public void setENDTIME(LocalDateTime eNDTIME) {
		ENDTIME = eNDTIME;
	}
	
}
