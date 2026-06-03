package com.yandex.filmorate.http.controller.rest;

import com.yandex.filmorate.entity.User;
import com.yandex.filmorate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createNewUser(@RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return user;
    }

    @GetMapping
    public Set<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
