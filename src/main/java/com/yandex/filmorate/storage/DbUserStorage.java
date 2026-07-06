package com.yandex.filmorate.storage;


import com.yandex.filmorate.model.User;
import com.yandex.filmorate.model.db.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DbUserStorage implements UserStorage{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFriendRepository userFriendRepository;

    @Autowired
    private UserMapper mapper;

    @Override
    public void addUser(User user) {
        UserEntity entity = mapper.map(user);
        userRepository.save(entity);
        user.setId(entity.getId());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        addUser(user);
        user = getUserById(user.getId());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(u-> mapper.unmap(u, getFriendsId(u.getId()))).collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long id) {
        Optional<UserEntity> maybeUserEntity = userRepository.findById(id);
        return maybeUserEntity.map(user -> mapper.unmap(user, getFriendsId(id))).orElse(null);
    }

    @Override
    public boolean isExist(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public void addFriend(User user, User friend) {
        UsersFriendsEntity entity1 =UsersFriendsEntity.builder()
                .userId(user.getId())
                .friendId(friend.getId())
                .build();
        UsersFriendsEntity entity2 =UsersFriendsEntity.builder()
                .userId(friend.getId())
                .friendId(user.getId())
                .build();
        userFriendRepository.save(entity1);
        userFriendRepository.save(entity2);
    }

    @Override
    public void deleteFriend(User user, User friend) {
        userFriendRepository.deleteByUserIdAndFriendId(user.getId(), friend.getId());
        userFriendRepository.deleteByUserIdAndFriendId(friend.getId(), user.getId());
    }

    @Override
    public Set<User> getFriends(User user) {
        Set<Long> ids = getFriendsId(user.getId());
        return ids.stream().map(this::getUserById).collect(Collectors.toSet());
    }

    private Set<Long> getFriendsId(Long userId) {
        return userFriendRepository.findFriendsByUserId(userId);
    }
}
