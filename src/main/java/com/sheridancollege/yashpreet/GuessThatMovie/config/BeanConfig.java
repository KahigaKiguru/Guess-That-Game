package com.sheridancollege.yashpreet.GuessThatMovie.config;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

	@Bean
	public Random getRandom() {
		return new Random();
	}
}
