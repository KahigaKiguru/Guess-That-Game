package com.sheridancollege.yashpreet.GuessThatMovie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sheridancollege.yashpreet.GuessThatMovie.service.PlayerService;

@Controller
public class PlayerController {

	@Autowired
	private PlayerService playerService;
	
	@GetMapping("/")
	public String showHomePage(Model model) {
		model.addAttribute("leaderboard", playerService.getAllPlayers());
		return "index";
	}
	
	@GetMapping("/startGame")
	public String startGame() {
		return "game_start";
	}
	
	@GetMapping("/registerPage")
	public String registerPage() {
		return "player_register";
	}
	
	@GetMapping("/loginPage")
	public String loginPage() {
		return "player_login";
	}
	
	
}
