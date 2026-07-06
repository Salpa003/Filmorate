package com.yandex.filmorate.service;


import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.model.User;
import com.yandex.filmorate.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
//    @Qualifier("inMemoryUserStorage")
    @Qualifier("dbUserStorage")
    private UserStorage userStorage;

    @Transactional
    public void addFriend(Long user, Long friend) {
        User user1 = getUserById(user);
        User user2 = getUserById(friend);
        if (user1 == null || user2 == null)
            throw new NotFoundException("");
        userStorage.addFriend(user1, user2);
    }
    @Transactional
    public void deleteFriend(Long user, Long friend) {
        User user1 = getUserById(user);
        User user2 = getUserById(friend);
        if (user1 == null || user2 == null)
            throw new NotFoundException("");
        userStorage.deleteFriend(user1, user2);
    }

    @Transactional(readOnly = true)
    public Set<User> getDoubleFriends(Long user, Long friend) {
        Set<User> friends = new HashSet<>();
        User friend1 = getUserById(friend);
        Set<Long> longs = friend1.getFriends();
        User user1 = getUserById(user);
        user1.getFriends().stream()
                .forEach(id -> {
                    if (longs.contains(id))
                        friends.add(getUserById(id));
                });
        return friends;
    }

    @Transactional
    public void addUser(User user) {
        userStorage.addUser(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userStorage.deleteUser(userId);
    }

    @Transactional
    public void updateUser(User user) {
        userStorage.updateUser(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    @Transactional(readOnly = true)
    public Set<User> getFriends(Long id) {
        User user = getUserById(id);
        if (user == null)
            throw new NotFoundException("");
        return userStorage.getFriends(user);
    }
    @Transactional(readOnly = true)
    public boolean isExist(Long id) {
        return userStorage.isExist(id);
    }
}
