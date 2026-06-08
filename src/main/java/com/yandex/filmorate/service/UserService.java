package com.yandex.filmorate.service;

import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.model.User;
import com.yandex.filmorate.model.UsersFriends;
import com.yandex.filmorate.repository.UsersFriendsRepository;
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

    @Autowired
    private UsersFriendsRepository usersFriendsRepository;

    public void addFriend(Long user, Long friend) {
        User user1 = getUserById(user);
        User user2 = getUserById(friend);
        if (user1 == null || user2 == null)
            throw new NotFoundException("");
        UsersFriends usersFriends1 = new UsersFriends(user1,user2);
        UsersFriends usersFriends2 = new UsersFriends(user2,user1);
        usersFriendsRepository.save(usersFriends1);
        usersFriendsRepository.save(usersFriends2);
    }

    public void deleteFriend(Long user, Long friend) {
        User user1 = getUserById(user);
        User user2 = getUserById(friend);
        if (user1 == null || user2 == null)
            throw new NotFoundException("");

        usersFriendsRepository.deleteByUserAndFriend(user1,user2);
        usersFriendsRepository.deleteByUserAndFriend(user2,user1);
    }

    public Set<User> getDoubleFriends(Long user, Long friend) {
        User user1 = getUserById(user);
        User user2 = getUserById(friend);
        if (user1 == null || user2 == null)
            throw new NotFoundException("");
        Set<User> friends = new HashSet<>();
        Set<Long> longs = user1.getFriends().stream().map(u-> u.getFriend().getId()).collect(Collectors.toSet());
        user2.getFriends().stream()
                .forEach( uf ->{
                    if (longs.contains(uf.getFriend().getId()))
                        friends.add(uf.getFriend());
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
        return user.getFriends().stream().map(uf -> uf.getFriend()).collect(Collectors.toSet());
    }

    public boolean isExist(Long id) {
        return userStorage.isExist(id);
    }
}
