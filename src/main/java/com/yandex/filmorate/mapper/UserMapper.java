package com.yandex.filmorate.mapper;

import com.yandex.filmorate.dto.UserReadDto;
import com.yandex.filmorate.entity.UserEntity;
import com.yandex.filmorate.dto.UserCreateDto;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserMapper {

    public UserReadDto toReadDto(UserEntity entity, Set<Long> friends) {
        return new UserReadDto(
                entity.getId(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getName(),
                entity.getBirthday(),
                friends
        );
    }

    public UserEntity toEntity(UserCreateDto dto) {
        return new UserEntity(
                null,
                dto.getEmail(),
                dto.getLogin(),
                dto.getName(),
                dto.getBirthday()
        );
    }
    public UserEntity toEntity(UserReadDto dto) {
        return new UserEntity(
                dto.getId(),
                dto.getEmail(),
                dto.getLogin(),
                dto.getName(),
                dto.getBirthday()
        );
    }
}
