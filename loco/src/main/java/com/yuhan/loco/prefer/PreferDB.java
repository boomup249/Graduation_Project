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
	private String action;
	private String shooting;
	private String adventure;
	private String fighting;
	private String roguelike;
	private String RPG;
	private String MMORPG;
	private String simulation;
	private String sports;
	private String puzzle;
	private String arcade;
	private String strat;
	private String horror;
	private String multi;
	private String single;
	
	
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getShooting() {
		return shooting;
	}
	public void setShooting(String shooting) {
		this.shooting = shooting;
	}
	public String getAdventure() {
		return adventure;
	}
	public void setAdventure(String adventure) {
		this.adventure = adventure;
	}
	public String getFighting() {
		return fighting;
	}
	public void setFighting(String fighting) {
		this.fighting = fighting;
	}
	public String getRoguelike() {
		return roguelike;
	}
	public void setRoguelike(String roguelike) {
		this.roguelike = roguelike;
	}
	public String getRPG() {
		return RPG;
	}
	public void setRPG(String rPG) {
		RPG = rPG;
	}
	public String getMMORPG() {
		return MMORPG;
	}
	public void setMMORPG(String mMORPG) {
		MMORPG = mMORPG;
	}
	public String getSimulation() {
		return simulation;
	}
	public void setSimulation(String simulation) {
		this.simulation = simulation;
	}
	public String getSports() {
		return sports;
	}
	public void setSports(String sports) {
		this.sports = sports;
	}
	public String getPuzzle() {
		return puzzle;
	}
	public void setPuzzle(String puzzle) {
		this.puzzle = puzzle;
	}
	public String getArcade() {
		return arcade;
	}
	public void setArcade(String arcade) {
		this.arcade = arcade;
	}
	public String getStrat() {
		return strat;
	}
	public void setStrat(String strat) {
		this.strat = strat;
	}
	public String getHorror() {
		return horror;
	}
	public void setHorror(String horror) {
		this.horror = horror;
	}
	public String getMulti() {
		return multi;
	}
	public void setMulti(String multi) {
		this.multi = multi;
	}
	public String getSingle() {
		return single;
	}
	public void setSingle(String single) {
		this.single = single;
	}

	
	
}
