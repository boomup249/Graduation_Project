package com.yuhan.loco.prefer;


import com.yuhan.loco.user.UserDB;
import com.yuhan.loco.user.UserDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
@Table(name = "prefer")
public class PreferDB {
	
	//장르
	@Id
	private String ID;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private UserDB userDB;
	private Boolean action;
	private Boolean action_adventure;
	private Boolean survival;
	private Boolean shooting;
	private Boolean FPS;
	private Boolean RPG;
	private Boolean ARPG;
	private Boolean MMORPG;
	private Boolean open_world;
	private Boolean hack_and_slash;
	private Boolean adventure;
	private Boolean sports;
	private Boolean racing;
	private Boolean casual;
	private Boolean puzzle;
	
	
	//getter setter
	//장르
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}
	public UserDB getuserDB() {
		return userDB;
	}
	public void setuserDB(UserDB userDB) {
		this.userDB = userDB;
		this.ID = userDB.getID();
	}
	public Boolean getAction() {
		return action;
	}
	public void setAction(Boolean action) {
		this.action = action;
	}
	public Boolean getAction_adventure() {
		return action_adventure;
	}
	public void setAction_adventure(Boolean action_adventure) {
		this.action_adventure = action_adventure;
	}
	public Boolean getSurvival() {
		return survival;
	}
	public void setSurvival(Boolean survival) {
		this.survival = survival;
	}
	public Boolean getShooting() {
		return shooting;
	}
	public void setShooting(Boolean shooting) {
		this.shooting = shooting;
	}
	public Boolean getFPS() {
		return FPS;
	}
	public void setFPS(Boolean fPS) {
		FPS = fPS;
	}
	public Boolean getRPG() {
		return RPG;
	}
	public void setRPG(Boolean rPG) {
		RPG = rPG;
	}
	public Boolean getARPG() {
		return ARPG;
	}
	public void setARPG(Boolean aRPG) {
		ARPG = aRPG;
	}
	public Boolean getMMORPG() {
		return MMORPG;
	}
	public void setMMORPG(Boolean mMORPG) {
		MMORPG = mMORPG;
	}
	public Boolean getOpen_world() {
		return open_world;
	}
	public void setOpen_world(Boolean open_world) {
		this.open_world = open_world;
	}
	public Boolean getHack_and_slash() {
		return hack_and_slash;
	}
	public void setHack_and_slash(Boolean hack_and_slash) {
		this.hack_and_slash = hack_and_slash;
	}
	public Boolean getAdventure() {
		return adventure;
	}
	public void setAdventure(Boolean adventure) {
		this.adventure = adventure;
	}
	public Boolean getSports() {
		return sports;
	}
	public void setSports(Boolean sports) {
		this.sports = sports;
	}
	public Boolean getRacing() {
		return racing;
	}
	public void setRacing(Boolean racing) {
		this.racing = racing;
	}
	public Boolean getCasual() {
		return casual;
	}
	public void setCasual(Boolean casual) {
		this.casual = casual;
	}
	public Boolean getPuzzle() {
		return puzzle;
	}
	public void setPuzzle(Boolean puzzle) {
		this.puzzle = puzzle;
	}
	
	
}