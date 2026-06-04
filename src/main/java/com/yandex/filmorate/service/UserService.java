package com.yandex.filmorate.service;

import com.yandex.filmorate.entity.User;
import com.yandex.filmorate.exception.UserValidateException;
import com.yandex.filmorate.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addUser(User user) {
        validate(user);
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user) {
        validate(user);
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
        userRepository.deleteById(id);
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
