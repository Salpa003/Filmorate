package com.yandex.filmorate.service;

import com.yandex.filmorate.entity.User;
import com.yandex.filmorate.entity.UsersFriends;
import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.exception.UserValidateException;
import com.yandex.filmorate.repository.UserRepository;
import com.yandex.filmorate.repository.UsersFriendsRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersFriendsRepository usersFriendsRepository;

    @Transactional
    public void addUser(User user) {
        validate(user);
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user) {
        validate(user);
        Optional<User> byId = userRepository.findById(user.getId());
        if (byId.isEmpty())
            throw new NotFoundException("User not found!");
        userRepository.save(user);
    }

    @Transactional
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public Set<User> getAllUsers() {
        return userRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Transactional
    public void deleteUser(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty())
           throw new NotFoundException("");
        userRepository.deleteById(id);
    }

    @Transactional
    public void addFriend(Long userId, Long friendId) {
        UsersFriends usersFriends1 = new UsersFriends(userId,friendId);
        UsersFriends usersFriends2 = new UsersFriends(friendId, userId);
        usersFriendsRepository.save(usersFriends1);
        usersFriendsRepository.save(usersFriends2);
    }

    @Transactional
    public void deleteFriend(Long userId, Long friendId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<User> friend = userRepository.findById(friendId);
        if (user.isEmpty() || friend.isEmpty())
            throw new NotFoundException("");
        usersFriendsRepository.removeByUserIdAndFriendId(userId,friendId);
        usersFriendsRepository.removeByUserIdAndFriendId(friendId,userId);
    }

    @Transactional
    public Set<Long> getDoubleFriends(Long user1id, Long user2id) {
        Set<Long> user1friends = usersFriendsRepository.getFriends(user1id);
        Set<Long> user2friends = usersFriendsRepository.getFriends(user2id);
        Set<Long> doubleFriends = new HashSet<>();
        for (Long a1 : user1friends) {
            for (Long a2: user2friends) {
                if (a1 == a2)
                    doubleFriends.add(a1);
            }
        }
        return doubleFriends;
    }

    @Transactional
    public Set<Long> getFriends(Long userId) {
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isEmpty())
            return new HashSet<>();
        return usersFriendsRepository.getFriends(userId);
    }
    public void validate(User user) {
        String errorMessage = null;
        if (user.getEmail().isBlank() || !user.getEmail().contains("@"))
            errorMessage = "email dont correct!";
        else if (user.getLogin().isBlank() || user.getLogin().contains(" "))
            errorMessage = "login dont correct!";
        else if (user.getBirthday().isAfter(LocalDate.now()))
            errorMessage = "birthday dont correct!";
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());

        if (errorMessage!= null) {
            log.warn("Validate error {} {}", user, errorMessage);
            throw new UserValidateException(errorMessage);
        }
    }
}
