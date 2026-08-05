package com.yandex.filmorate.controller;


import com.yandex.filmorate.dto.UserReadDto;
import com.yandex.filmorate.service.UserService;
import com.yandex.filmorate.dto.UserCreateDto;
import com.yandex.filmorate.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserReadDto> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserReadDto getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserReadDto createUser(@RequestBody UserCreateDto dto) {
       Long id = userService.addUser(dto);
        log.info("Create new user ({})", dto);
        return userService.getUserById(id);
    }

    @PutMapping
    public UserReadDto updateUser(@RequestBody UserReadDto user) {
        if (!userService.isExist(user.getId())) {
            throw new NotFoundException("Not found my");
        }
        userService.updateUser(user);
        log.info("Update user ({})", user);
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public UserReadDto addFriend(@PathVariable("id") Long id, @PathVariable("friendId") Long friendId) {
        userService.addFriend(id,friendId);
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public UserReadDto deleteFriend(@PathVariable("id") Long id, @PathVariable("friendId") Long friendId) {
        userService.deleteFriend(id,friendId);
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public Set<UserReadDto> getFriends(@PathVariable("id") Long id) {
       return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<UserReadDto> getDoubleFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getDoubleFriends(id,otherId);
    }

}
