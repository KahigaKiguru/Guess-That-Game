package com.sheridancollege.yashpreet.GuessThatMovie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "players")
public class Player {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "player_id")
	private int id;
	
	@Column(name = "player_name")
	private String name;
	
	@Column(name =  "games_played")
	private int gamesPlayed;
	
	@Column(name = "games_lost")
	private int gamesLost;
	
	@Column(name = "total_score")
	private int totalScore;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public int getGamesLost() {
		return gamesLost;
	}

	public void setGamesLost(int gamesLost) {
		this.gamesLost = gamesLost;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	
	
	
	
}
