package com.yandex.filmorate.controller;


import com.yandex.filmorate.dto.UserReadDto;
import com.yandex.filmorate.service.UserService;
import com.yandex.filmorate.dto.UserCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
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
        return userService.addUser(dto);
    }

    @PutMapping
    public UserReadDto updateUser(@RequestBody UserReadDto user) {
       return userService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public UserReadDto addFriend(@PathVariable("id") Long id, @PathVariable("friendId") Long friendId) {
       return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public UserReadDto deleteFriend(@PathVariable("id") Long id, @PathVariable("friendId") Long friendId) {
       return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<UserReadDto> getFriends(@PathVariable("id") Long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<UserReadDto> getDoubleFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getDoubleFriends(id, otherId);
    }

}
