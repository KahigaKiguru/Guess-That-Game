package com.sheridancollege.yashpreet.GuessThatMovie.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sheridancollege.yashpreet.GuessThatMovie.model.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer>{

}
