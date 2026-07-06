package com.yandex.filmorate.storage;


import com.yandex.filmorate.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Long, User> users = new HashMap<>();
    private Long id = 1L;

    @Override
    public void addUser(User user) {
        user.setId(id);
        user.setFriends(new HashSet<>());
        users.put(id++,user);
    }

    @Override
    public void deleteUser(Long id) {
        users.remove(id);
    }

    @Override
    public void updateUser(User user) {
        users.replace(user.getId(),user);
    }
    @Override
    public List<User> getAllUsers() {
        return users.values().stream().toList();
    }

    @Override
    public User getUserById(Long id) {
        return users.get(id);
    }

    @Override
    public boolean isExist(Long id) {
        return users.containsKey(id);
    }

    @Override
    public void addFriend(User user, User friend) {
        Set<Long> friends = user.getFriends();
        friends.add(friend.getId());
        Set<Long> friends1 = friend.getFriends();
        friends1.add(user.getId());
    }

    @Override
    public void deleteFriend(User user1, User user2) {
        Set<Long> friends = user1.getFriends();
        friends.remove(user2.getId());
        Set<Long> friends2 = user2.getFriends();
        friends2.remove(user1.getId());
    }

    @Override
    public Set<User> getFriends(User user) {
        return user.getFriends().stream()
                .map(l -> getUserById(l))
                .collect(Collectors.toSet());
    }
}