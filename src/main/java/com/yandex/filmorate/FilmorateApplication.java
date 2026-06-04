package com.yandex.filmorate;

import com.yandex.filmorate.entity.User;
import com.yandex.filmorate.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class FilmorateApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FilmorateApplication.class, args);
    }

}
