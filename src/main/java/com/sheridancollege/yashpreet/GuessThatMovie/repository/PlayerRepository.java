package com.sheridancollege.yashpreet.GuessThatMovie.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sheridancollege.yashpreet.GuessThatMovie.model.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer>{

	Player findByName(String name);
}
