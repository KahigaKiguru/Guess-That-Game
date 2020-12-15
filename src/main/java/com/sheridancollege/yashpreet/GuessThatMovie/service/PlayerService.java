package com.sheridancollege.yashpreet.GuessThatMovie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheridancollege.yashpreet.GuessThatMovie.model.Player;
import com.sheridancollege.yashpreet.GuessThatMovie.repository.PlayerRepository;

@Service
public class PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;

//	create, update, delete, get by Id, get all
	
	public void createPlayer(Player player) {
		player.setGamesLost(0);
		player.setGamesPlayed(0);
		player.setTotalScore(0);
		
		playerRepository.save(player);
	}
	
	public void updatePlayer(Player player) {
		playerRepository.save(player);
	}
	
	public Player getPlayerById(int id) {
		return playerRepository.findById(id).get();
	}
	
	public Iterable<Player> getAllPlayers(){
		return playerRepository.findAll();
	}
	
	public void deletePlayer(int id) {
		playerRepository.deleteById(id);
	}
}
