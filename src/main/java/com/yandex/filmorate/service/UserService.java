package com.yandex.filmorate.service;


import com.yandex.filmorate.dto.UserReadDto;
import com.yandex.filmorate.entity.UserEntity;
import com.yandex.filmorate.entity.UsersFriendsEntity;
import com.yandex.filmorate.repository.UserFriendRepository;
import com.yandex.filmorate.repository.UserRepository;
import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.dto.UserCreateDto;
import com.yandex.filmorate.exception.ValidationException;
import com.yandex.filmorate.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFriendRepository userFriendRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void addFriend(Long user, Long friend) {
        boolean exists1 = userRepository.existsById(user);
        boolean exists2 = userRepository.existsById(friend);
        if (exists1 && exists2) {
            UsersFriendsEntity entity = new UsersFriendsEntity(user, friend);
            userFriendRepository.save(entity);
        } else {
            throw new NotFoundException("");
        }

    }

    @Transactional
    public void deleteFriend(Long user, Long friend) {
        boolean exists1 = userRepository.existsById(user);
        boolean exists2 = userRepository.existsById(friend);
        if (exists1 && exists2) {
            userFriendRepository.deleteByUserIdAndFriendId(user, friend);
        } else {
            throw new NotFoundException("");
        }
    }

    @Transactional(readOnly = true)
    public Set<UserReadDto> getDoubleFriends(Long user, Long friend) {
        boolean exists1 = userRepository.existsById(user);
        boolean exists2 = userRepository.existsById(friend);
        if (!exists1 || !exists2)
            throw new NotFoundException("");

        Set<Long> friends1 = userFriendRepository.findFriendsByUserId(user);
        Set<Long> friends2 = userFriendRepository.findFriendsByUserId(friend);
        Set<Long> friends = friends1.stream()
                .filter(friends2::contains)
                .collect(Collectors.toSet());

        Set<UserReadDto> dtos = new HashSet<>();
        for (Long id : friends) {
            dtos.add(getUserById(id));
        }
        return dtos;
    }

    @Transactional
    public Long addUser(UserCreateDto dto) {
        validateUser(dto);
        UserEntity entity = userMapper.toEntity(dto);
        userRepository.save(entity);
        return entity.getId();
    }

//    @Transactional
//    public void deleteUser(Long userId) {
//        userStorage.deleteUser(userId);
//    }

    @Transactional
    public void updateUser(UserReadDto user) {
        validateUser(user);
        UserEntity entity = userMapper.toEntity(user);
        userRepository.save(entity);
//        Set<Long> friends = user.getFriends();
//        Set<Long> friendsOld = userFriendRepository.findFriendsByUserId(user.getId());
    }

    @Transactional(readOnly = true)
    public List<UserReadDto> getAllUsers() {
        List<UserEntity> entityList = userRepository.findAll();
        List<UserReadDto> dtos = new ArrayList<>();
        for (UserEntity entity : entityList) {
            Set<Long> friends = userFriendRepository.findFriendsByUserId(entity.getId());
            dtos.add(userMapper.toReadDto(entity, friends));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public UserReadDto getUserById(Long id) {
        UserEntity entity = userRepository.findById(id).get();
        Set<Long> friends = userFriendRepository.findFriendsByUserId(id);
        return userMapper.toReadDto(entity, friends);
    }

    @Transactional(readOnly = true)
    public Set<UserReadDto> getFriends(Long id) {
        if (!userRepository.existsById(id))
            throw new NotFoundException("");

        Set<Long> ids = userFriendRepository.findFriendsByUserId(id);
        Set<UserReadDto> friends = new HashSet<>();
        for (Long id1 : ids) {
            friends.add(getUserById(id1));
        }
        return friends;
    }

    @Transactional(readOnly = true)
    public boolean isExist(Long id) {
        return userRepository.existsById(id);
    }

    public void validateUser(UserCreateDto user) {
        String message = null;
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@"))
            message = "Не корректная почта";
        if (user.getLogin().isEmpty() || user.getLogin().contains(" "))
            message = "Не корректный логин (возможно в нем есть пробелы)";
        if (user.getBirthday().isAfter(LocalDate.now()))
            message = "Не верная дата рождения";
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());

        if (message != null) {
//            log.info("Ошибка валидации пользоватля, {}", message);
            throw new ValidationException(message);
        }
    }

    public void validateUser(UserReadDto user) {
        String message = null;
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@"))
            message = "Не корректная почта";
        if (user.getLogin().isEmpty() || user.getLogin().contains(" "))
            message = "Не корректный логин (возможно в нем есть пробелы)";
        if (user.getBirthday().isAfter(LocalDate.now()))
            message = "Не верная дата рождения";
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());

        if (message != null) {
//            log.info("Ошибка валидации пользоватля, {}", message);
            throw new ValidationException(message);
        }
    }
}
