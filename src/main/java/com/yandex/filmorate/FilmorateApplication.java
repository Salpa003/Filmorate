package com.yandex.filmorate;

import com.yandex.filmorate.model.Film;
import com.yandex.filmorate.model.User;
import com.yandex.filmorate.model.db.FilmEntity;
import com.yandex.filmorate.model.db.FilmMapper;
import com.yandex.filmorate.model.db.FilmRepository;
import com.yandex.filmorate.model.db.UserFriendRepository;
import com.yandex.filmorate.service.FilmService;
import com.yandex.filmorate.service.UserService;
import com.yandex.filmorate.storage.DbUserStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class FilmorateApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(FilmorateApplication.class, args);
	}

}
