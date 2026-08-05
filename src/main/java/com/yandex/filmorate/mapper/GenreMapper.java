package com.yandex.filmorate.mapper;

import com.yandex.filmorate.dto.GenreReadDto;
import com.yandex.filmorate.entity.GenreEntity;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public GenreReadDto toReadDto(GenreEntity entity) {
        return new GenreReadDto(entity.getId(), entity.getName());
    }
}
