package com.sheridancollege.yashpreet.GuessThatMovie.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sheridancollege.yashpreet.GuessThatMovie.model.Movie;
import com.sheridancollege.yashpreet.GuessThatMovie.model.Player;
import com.sheridancollege.yashpreet.GuessThatMovie.service.MovieService;
import com.sheridancollege.yashpreet.GuessThatMovie.service.PlayerService;

@Controller
public class PlayerController {

	private int max_tries = 7;
	private int tries = 0;
	
	private int number_of_letters_in_movie = 0;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private MovieService movieService;

	@Autowired
	private Random random;

	@ModelAttribute("player")
	public Player playerBinding() {
		return new Player();
	}

	@ModelAttribute("movie")
	public Movie movieBinding() {
		return new Movie();
	}

	@GetMapping("/")
	public String showLogin() {
		return "redirect:/loginPage";
	}

	@GetMapping("/home")
	public String showHomePage(@RequestParam("player_id") int player_id, Model model) {
		List<Player> players = new ArrayList<Player>();

		Player player = playerService.getPlayerById(player_id);

		for (Player pl : playerService.getAllPlayers()) {
			if (pl.getGamesPlayed() > 0) {
				players.add(pl);
			}
		}
		model.addAttribute("player", player);
		model.addAttribute("players", players);
		return "index";
	}

	@GetMapping("/registerPage")
	public String registerPage() {
		return "player_register";
	}

	@PostMapping("/register")
	public String registerPlayer(@ModelAttribute("player") Player player, Model model) {
		if (playerService.getPlayerByName(player.getName()) != null) {
			return "redirect:/registerPage?username_not_available";
		} else {
			model.addAttribute("player", playerService.createPlayer(player));
			return "redirect:/loginPage?registered";
		}
	}

	@GetMapping("/loginPage")
	public String loginPage() {
		return "player_login";
	}

	@PostMapping("/login")
	public String login(String name, Model model) {
		Player player = playerService.getPlayerByName(name);
		if (player != null) {
			model.addAttribute("player", playerService.getPlayerByName(name));
			return "redirect:/home?player_id=" + player.getId();
		}
		return "redirect:/loginPage?user_does_not_exist";
	}

	@GetMapping("/playPage")
	public String showPlayPage() {
		return "game_start";
	}

	@GetMapping("/showUpdatePage")
	public String showUpdatePage(@RequestParam("player_id") int player_id, Model model) {
		model.addAttribute("player", playerService.getPlayerById(player_id));
		return "player_update";
	}
	
	@GetMapping("/deletePlayer")
	public String deletePlayer(@RequestParam("player_id") int player_id, Model model) {
		playerService.deletePlayer(player_id);
		Player admin = playerService.getPlayerByName("Admin");
		return "redirect:/home?player_id=" + admin.getId();
	}

	@PostMapping("/updatePlayer")
	public String updatePlayer(@RequestParam("player_id") int player_id, @ModelAttribute("player") Player pl) {
		Player player = playerService.getPlayerById(player_id);

		player.setName(pl.getName());
		player.setGamesLost(pl.getGamesLost());
		player.setGamesPlayed(pl.getGamesPlayed());
		player.setGamesWon(pl.getGamesWon());
		player.setPosition(pl.getPosition());
		player.setTotalScore(pl.getTotalScore());

		playerService.updatePlayer(player);

		Player admin = playerService.getPlayerByName("Admin");
		return "redirect:/home?player_id=" + admin.getId();
	}

	@GetMapping("/startGame")
	public String startGame(@RequestParam("player_id") int player_id, Model model) {

		Player player = playerService.getPlayerById(player_id);

		List<Movie> movies = (List<Movie>) movieService.getAllMovies();

		Movie movie = movies.get(random.nextInt(movies.size()));

		if (movies.size() > 0 && player != null) {
			model.addAttribute("remaining_tries", max_tries);
			model.addAttribute("movie", movie);
			model.addAttribute("player", player);
			return "game_start";
		}
		return "redirect:/home?player_id=" + player.getId();

	}

	@GetMapping("/showStartUnregisteredGamePage")
	public String showUnregisteredStartGame(Model model) {

		List<Movie> movies = (List<Movie>) movieService.getAllMovies();

		Movie movie = movies.get(random.nextInt(movies.size()));

		model.addAttribute("movie", movie);
		model.addAttribute("remaining_tries", max_tries);
		return "game_start_unregistered";
	}

	@PostMapping("/play")
	public String playGame(@RequestParam("player_id") int player_id, @RequestParam("letter") String letter,
			@RequestParam("movie_id") int movie_id, Model model) {

		Movie movie = movieService.getMovieById(movie_id);

		Player player = playerService.getPlayerById(player_id);

		model.addAttribute("remaining_tries", max_tries);

		int movie_length = movie.getName().length();
		char letter_c = letter.charAt(0);
		
		for (int i = 0; i < movie_length; i++) {
			if(movie.getName().charAt(i) == letter_c) {
				number_of_letters_in_movie += 1;
			}
		}
		
		
//		handle errors
		if (letter.length() > 1 || letter.isBlank()) {
			return "redirect:/startGame?wrong_input";
		}

//		handle game won
		if (movie.getName().contains(letter)) {
			player.setGamesPlayed(player.getGamesPlayed() + 1);
			player.setGamesWon(player.getGamesWon() + 1);
			player.setTotalScore(player.getTotalScore() + 5);
				
			return "redirect:/guessMoviePage?player_id=" + player.getId() + "&movie_id=" + movie.getId();

		} else if (!movie.getName().contains(letter)) {
//			handle game lost	
			return "redirect:/wrongGuess?player_id=" + player.getId() + "&movie_id=" + movie.getId();
		}
		return letter;

	}

	@PostMapping("/playUnregistered")
	public String playUnregistered(@RequestParam("letter") String letter, @RequestParam("movie_id") int movie_id,
			Model model) {

		Movie movie = movieService.getMovieById(movie_id);

//		handle errors
		if (letter.length() > 1 || letter.isBlank()) {
			return "redirect:/startUnregisteredGame?wrong_input";
		}

//		handle game won
		if (movie.getName().contains(letter)) {

			model.addAttribute("movie", movieService.getMovieById(movie_id));
			int movie_length = movie.getName().length();
			char letter_c = letter.charAt(0);
			for (int i = 0; i < movie_length; i++) {
				if(movie.getName().charAt(i) == letter_c) {
					number_of_letters_in_movie += 1;
				}
			}
			
			return "redirect:/guessMoviePageUnregistered?movie_id=" + movie.getId();

		} else if (!movie.getName().contains(letter)) {
//			handle game lost	
			return "redirect:/wrongGuessUnregistered?movie_id=" + movie.getId() + "&letter=" + letter;
		}
		return letter;

	}

	@GetMapping("/guessMoviePage")
	public String guessMoviePage(
			@RequestParam("player_id") int player_id, 
			@RequestParam("movie_id") int movie_id,
			Model model) {
		model.addAttribute("player", playerService.getPlayerById(player_id));
		model.addAttribute("movie", movieService.getMovieById(movie_id));
		model.addAttribute("number_of_letters", number_of_letters_in_movie);
		number_of_letters_in_movie = 0;
		return "movie_guess";
	}

	@GetMapping("/guessMoviePageUnregistered")
	public String guessMoviePageUnregistered(@RequestParam("movie_id") int movie_id, Model model) {
		model.addAttribute("movie", movieService.getMovieById(movie_id));
		model.addAttribute("number_of_letters", number_of_letters_in_movie);
		number_of_letters_in_movie = 0;
		return "movie_guess_unregistered";
	}

	@PostMapping("/guessName")
	public String guessName(
			@RequestParam("movie_id") int movie_id,
			
			@RequestParam("player_id") int player_id,
			@RequestParam("name") String name) {
		Player player = playerService.getPlayerById(player_id);
		Movie movie = movieService.getMovieById(movie_id);

//		handle errors
		if (name.isEmpty() || name.isBlank()) {
			return "redirect:/guessMoviePage?wrong_input";
		}

//		handle game won
		if (movie.getName().equalsIgnoreCase(name)) {
			player.setGamesPlayed(player.getGamesPlayed() + 1);
			player.setGamesWon(player.getGamesWon() + 1);
			player.setTotalScore(player.getTotalScore() + 5);

			playerService.updatePlayer(player);
			number_of_letters_in_movie = 0;
			return "redirect:/home?player_id=" + player.getId() + "&game_won";

		} else {
//			handle game lost
			player.setGamesPlayed(player.getGamesPlayed() + 1);
			player.setGamesLost(player.getGamesWon() + 1);
			player.setTotalScore(player.getTotalScore() - 5);
			number_of_letters_in_movie = 0;
			return "redirect:/home?player_id=" + player.getId() + "&game_lost";
		}
	}

	@PostMapping("/guessNameUnregistered")
	public String guessNameUnregistered(@RequestParam("movie_id") int movie_id, @RequestParam("name") String name,
			Model model) {

		Movie movie = movieService.getMovieById(movie_id);

//		handle errors
		if (name.isEmpty() || name.isBlank()) {
			return "redirect:/startUnregisteredGame?wrong_input";
		}

//		handle game won
		if (movie.getName().equalsIgnoreCase(name)) {
			model.addAttribute("message", "win");
			number_of_letters_in_movie = 0;
			return "game_over";

		} else {
//			handle game lost			
			model.addAttribute("message", "lose");
			model.addAttribute("number_of_letters", number_of_letters_in_movie);
			number_of_letters_in_movie = 0;
			return "game_over";
		}
	}

	@GetMapping("/wrongGuess")
	public String wrongGuess(@RequestParam("player_id") int player_id, @RequestParam("movie_id") int movie_id,
			Model model) {

		Player player = playerService.getPlayerById(player_id);

		Movie movie = movieService.getMovieById(movie_id);

		if (!(tries < 0)) {
			for (int a = tries; a < max_tries;) {
				tries += 1;
				model.addAttribute("remaining_tries", max_tries - tries);
				
				model.addAttribute("movie", movie);
				model.addAttribute("player", player);

				return "game_start"; 
			}
		} 
			player.setGamesPlayed(player.getGamesPlayed() + 1);
			player.setGamesLost(player.getGamesLost() + 1);
			player.setTotalScore(player.getTotalScore() - 5);

			playerService.updatePlayer(player);
			
			model.addAttribute("player", playerService.getPlayerById(player_id));
			
			tries = 0;

			return "redirect:/home?player_id=" + player.getId() + "&game_lost";
		
		
	}

	

	@GetMapping("/wrongGuessUnregistered")
	public String wrongGuessUnregistered(@RequestParam("movie_id") int movie_id, @RequestParam("letter") String letter,
			Model model) {

		Movie movie = movieService.getMovieById(movie_id);
		model.addAttribute("remaining_tries", max_tries);
		
		if (!(tries < 0)) {
			for (int a = tries; a < max_tries;) {
				tries += 1;
				model.addAttribute("remaining_tries", max_tries - tries);
				model.addAttribute("movie", movie);

				return "game_start_unregistered"; 
			}
		} 
			

		model.addAttribute("message", "lose");
		tries = 0;
		return "game_over";
	}
}
