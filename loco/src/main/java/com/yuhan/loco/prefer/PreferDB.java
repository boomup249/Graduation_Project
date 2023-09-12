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
	private Boolean shooting;
	private Boolean adventure;
	private Boolean fighting;
	private Boolean roguelike;
	private Boolean RPG;
	private Boolean MMORPG;
	private Boolean simulation;
	private Boolean sports;
	private Boolean puzzle;
	private Boolean arcade;
	private Boolean strat;
	private Boolean horror;
	private Boolean multi;
	private Boolean single;
	
	
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
	public Boolean getShooting() {
		return shooting;
	}
	public void setShooting(Boolean shooting) {
		this.shooting = shooting;
	}
	public Boolean getAdventure() {
		return adventure;
	}
	public void setAdventure(Boolean adventure) {
		this.adventure = adventure;
	}
	public Boolean getFighting() {
		return fighting;
	}
	public void setFighting(Boolean fighting) {
		this.fighting = fighting;
	}
	public Boolean getRoguelike() {
		return roguelike;
	}
	public void setRoguelike(Boolean roguelike) {
		this.roguelike = roguelike;
	}
	public Boolean getRPG() {
		return RPG;
	}
	public void setRPG(Boolean rPG) {
		RPG = rPG;
	}
	public Boolean getMMORPG() {
		return MMORPG;
	}
	public void setMMORPG(Boolean mMORPG) {
		MMORPG = mMORPG;
	}
	public Boolean getSimulation() {
		return simulation;
	}
	public void setSimulation(Boolean simulation) {
		this.simulation = simulation;
	}
	public Boolean getSports() {
		return sports;
	}
	public void setSports(Boolean sports) {
		this.sports = sports;
	}
	public Boolean getPuzzle() {
		return puzzle;
	}
	public void setPuzzle(Boolean puzzle) {
		this.puzzle = puzzle;
	}
	public Boolean getArcade() {
		return arcade;
	}
	public void setArcade(Boolean arcade) {
		this.arcade = arcade;
	}
	public Boolean getStrat() {
		return strat;
	}
	public void setStrat(Boolean strat) {
		this.strat = strat;
	}
	public Boolean getHorror() {
		return horror;
	}
	public void setHorror(Boolean horror) {
		this.horror = horror;
	}
	public Boolean getMulti() {
		return multi;
	}
	public void setMulti(Boolean multi) {
		this.multi = multi;
	}
	public Boolean getSingle() {
		return single;
	}
	public void setSingle(Boolean single) {
		this.single = single;
	}

	
	
}
