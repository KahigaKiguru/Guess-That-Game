package com.sheridancollege.yashpreet.GuessThatMovie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheridancollege.yashpreet.GuessThatMovie.model.Movie;
import com.sheridancollege.yashpreet.GuessThatMovie.repository.MovieRepository;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;

//	create, update, delete, get all, get by id
	
	public void createMovie(Movie movie) {
		movieRepository.save(movie);
	}
	
	public void updateMovie(Movie movie) {
		movieRepository.save(movie);
	}
	
	public Movie getMovieById(int id) {
		return movieRepository.findById(id).get();
	}
	
	public Iterable<Movie> getAllMovies(){
		return movieRepository.findAll();
	}
	
	public void deleteMovie(int id) {
		movieRepository.deleteById(id);
	}
}
