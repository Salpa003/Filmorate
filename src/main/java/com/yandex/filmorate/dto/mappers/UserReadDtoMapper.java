package com.yandex.filmorate.dto.mappers;

import com.yandex.filmorate.dto.users.UserReadDto;
import com.yandex.filmorate.entity.User;
import com.yandex.filmorate.repository.UserRepository;
import com.yandex.filmorate.repository.UsersFriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserReadDtoMapper implements Mapper<User, UserReadDto> {
    @Autowired
    private UsersFriendsRepository usersFriendsRepository;
    @Override
    public UserReadDto map(User user) {
        Set<Long> friends = usersFriendsRepository.getFriends(user.getId());
        return new UserReadDto(user, friends);
    }

    @Override
    public User unmap(UserReadDto userReadDto) {
        return null;
    }
}
