 package com.sheridancollege.yashpreet.GuessThatMovie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sheridancollege.yashpreet.GuessThatMovie.model.Movie;
import com.sheridancollege.yashpreet.GuessThatMovie.model.Player;
import com.sheridancollege.yashpreet.GuessThatMovie.service.MovieService;
import com.sheridancollege.yashpreet.GuessThatMovie.service.PlayerService;

@SpringBootApplication
public class GuessThatMovieApplication implements CommandLineRunner {
 
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private PlayerService playerService;
	
	public static void main(String[] args) {
		SpringApplication.run(GuessThatMovieApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Movie movie1 = new Movie();
		movie1.setName("Good Will Hunting");
		
		movieService.createMovie(movie1);
		
		Movie movie2 = new Movie();
		movie2.setName("Interstellar");
		
		movieService.createMovie(movie2);
		
		Movie movie3 = new Movie();
		movie3.setName("Avatar");
		
		movieService.createMovie(movie3);
		
		Movie movie4 = new Movie();
		movie4.setName("The good life");
		
		movieService.createMovie(movie4);
		
		Movie movie5 = new Movie();
		movie5.setName("Forrest Gump");
		
		movieService.createMovie(movie5);
		
		Player admin = new Player();
		
		admin.setName("Admin");
		admin.setGamesWon(30);
		admin.setGamesPlayed(50);
		admin.setGamesLost(20);
		admin.setPosition(1);
		admin.setTotalScore(admin.getGamesWon()*1 - admin.getGamesLost());
		
		playerService.createPlayer(admin);
		
		Player kodi = new Player();
		
		kodi.setName("Kodi");
		kodi.setGamesWon(0);
		kodi.setGamesPlayed(0);
		kodi.setGamesLost(0);
		kodi.setPosition(2);
		kodi.setTotalScore(admin.getGamesWon()*1 - admin.getGamesLost());
		
		playerService.createPlayer(kodi);
	}

}
