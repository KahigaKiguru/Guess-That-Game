package com.sheridancollege.yashpreet.GuessThatMovie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sheridancollege.yashpreet.GuessThatMovie.model.Movie;
import com.sheridancollege.yashpreet.GuessThatMovie.service.MovieService;

@Controller
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@ModelAttribute("movie")
	public Movie movieBinding() {
		return new Movie();
	}
	
	@GetMapping("/movieList")
	public String moviesPage(Model model) {
		model.addAttribute("movie_list", movieService.getAllMovies());
		return "movie_list";
	}
	
	@GetMapping("/addMoviePage")
	public String addMoviePage() {
		return "movie_add";
	}
	@PostMapping("/addMovie")
	public String addMovie(@ModelAttribute("movie") Movie movie) {
		movieService.createMovie(movie);
		return "redirect:/movieList?movieAdded";
	}
	
	@GetMapping("/updateMoviePage")
	public String updatePage(@RequestParam("movie_id") int movie_id, Model model) {
		model.addAttribute("movie", movieService.getMovieById(movie_id));
		return "movie_update";
	}
	
	@PostMapping("/updateMovie")
	public String updateMovie(@RequestParam("movie_id") int movie_id, @ModelAttribute("movie") Movie mov) {
		Movie movie = movieService.getMovieById(movie_id);
		movie.setName(mov.getName());
		
		movieService.updateMovie(movie);
		
		return "redirect:/movieList?movieUpdated";
	
	}
	
	@GetMapping("/deleteMovie")
	public String deleteMovie(@RequestParam("movie_id") int movie_id) {
		movieService.deleteMovie(movie_id);
		return "movieList";
	}
}
