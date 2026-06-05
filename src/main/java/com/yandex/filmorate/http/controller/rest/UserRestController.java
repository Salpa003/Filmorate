//package com.yandex.filmorate.http.controller.rest;
//
//import com.yandex.filmorate.entity.User;
//import com.yandex.filmorate.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Set;
//
//@RestController
//@RequestMapping("/users")
//public class UserRestController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping
//    public User createNewUser(@RequestBody User user) {
//        userService.addUser(user);
//        return user;
//    }
//
//    @PutMapping
//    public User updateUser(@RequestBody User user) {
//        userService.updateUser(user);
//        return user;
//    }
//
//    @GetMapping
//    public Set<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @PutMapping("/{id}/friends/{friendId}")
//    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
//        userService.addFriend(id,friendId);
//        return userService.getUserById(friendId);
//    }
//
//    @DeleteMapping("/{id}/friends/{friendId}")
//    public User deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
//        userService.deleteFriend(id,friendId);
//        return userService.getUserById(id);
//    }
//
//    @GetMapping("/{id}/friends")
//    public Set<Long> getFriends(@PathVariable Long id) {
//        return userService.getFriends(id);
//    }
//
//    @GetMapping("/{id}/friends/common/{otherId}")
//    public Set<User> getDoubleFriends(@PathVariable Long id, @PathVariable Long otherId) {
//        return userService.getDoubleFriends(id,otherId);
//    }
//
//}
