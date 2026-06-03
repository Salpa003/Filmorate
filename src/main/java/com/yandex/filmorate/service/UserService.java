package com.yandex.filmorate.service;

import com.yandex.filmorate.entity.User;
import com.yandex.filmorate.exception.UserValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    public void addUser(User user) {

    }

    public void updateUser(User user) {

    }

    public Set<User> getAllUsers() {
        return null;
    }

    public void validate(User user) {
        String errorMessage = null;
        if (user.getEmail().isBlank() || !user.getEmail().contains("@"))
            errorMessage = "email dont correct!";
        else if (user.getLogin().isBlank() || user.getLogin().contains(" "))
            errorMessage = "login dont correct!";
        else if (user.getBirthday().isAfter(LocalDate.now()))
            errorMessage = "birthday dont correct!";
        if (user.getName().isBlank())
            user.setName(user.getLogin());

        if (errorMessage!= null) {
            log.warn("Validate error {} {}", user, errorMessage);
            throw new UserValidateException(errorMessage);
        }
    }
}
