package com.yandex.filmorate.storage;





import com.yandex.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {

    void addUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    boolean isExist(Long id);

    void addFriend(User user, User friend);
    void deleteFriend(User user, User friend);

    Set<User> getFriends(User user);
}