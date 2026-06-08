package com.yandex.filmorate.service;

import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.model.User;
import com.yandex.filmorate.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    @Qualifier("userDbStorage")
    private UserStorage userStorage;

    public void addFriend(Long user, Long friend) {
        User user1 = getUserById(user);
        User user2 = getUserById(friend);
        if (user1 == null || user2 == null)
            throw new NotFoundException("");
        Set<Long> friends = user1.getFriends();
        friends.add(friend);
        Set<Long> friends1 = user2.getFriends();
        friends1.add(user);
    }

    public void deleteFriend(Long user, Long friend) {
        User user1 = getUserById(user);
        User user2 = getUserById(friend);
        if (user1 == null || user2 == null)
            throw new NotFoundException("");
        Set<Long> friends = user1.getFriends();
        friends.remove(friend);
        Set<Long> friends2 = user2.getFriends();
        friends2.remove(user);
    }

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
    public void addUser(User user) {
        userStorage.addUser(user);
    }

    public void deleteUser(Long userId) {
        userStorage.deleteUser(userId);
    }

    public void updateUser(User user) {
        userStorage.updateUser(user);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    public Set<User> getFriends(Long id) {
        User user = getUserById(id);
        if (user == null)
            throw new NotFoundException("");
        return user.getFriends().stream()
                .map(l-> getUserById(l))
                .collect(Collectors.toSet());
    }

    public boolean isExist(Long id) {
        return userStorage.isExist(id);
    }
}
