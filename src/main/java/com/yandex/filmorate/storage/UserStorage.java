package com.yandex.filmorate.storage;


import com.yandex.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    void addUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    boolean isExist(Long id);
}