package com.yandex.filmorate.storage;

import com.yandex.filmorate.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
}