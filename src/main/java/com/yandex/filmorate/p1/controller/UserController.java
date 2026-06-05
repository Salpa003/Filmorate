package com.yandex.filmorate.p1.controller;


import com.yandex.filmorate.p1.exception.NotFoundException;
import com.yandex.filmorate.p1.exception.ValidationException;
import com.yandex.filmorate.p1.model.User;
import com.yandex.filmorate.p1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        validateUser(user);
        userService.addUser(user);
        log.info("Create new user ({})", user);
        return user;
    }

    @PutMapping
    public User updateUser( @RequestBody User user) {
        if (!userService.isExist(user.getId())) {
            throw new NotFoundException("Not found my");
        }
        validateUser(user);
        userService.updateUser(user);
        log.info("Update user ({})", user);
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") Long id, @PathVariable("friendId") Long friendId) {
        userService.addFriend(id,friendId);
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable("id") Long id, @PathVariable("friendId") Long friendId) {
        userService.deleteFriend(id,friendId);
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable("id") Long id) {
       return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getDoubleFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getDoubleFriends(id,otherId);
    }

    public void validateUser(User user) {
        String message = null;
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@"))
            message = "Не корректная почта";
        if (user.getLogin().isEmpty() || user.getLogin().contains(" "))
            message = "Не корректный логин (возможно в нем есть пробелы)";
        if (user.getBirthday().isAfter(LocalDate.now()))
            message = "Не верная дата рождения";
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());

        if (message != null) {
            log.info("Ошибка валидации пользоватля, {}", message);
            throw new ValidationException(message);
        }
    }
}
