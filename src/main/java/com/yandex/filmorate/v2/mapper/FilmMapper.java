package com.yandex.filmorate.v2.mapper;

import com.yandex.filmorate.v2.dto.FilmCreateDto;
import com.yandex.filmorate.v2.dto.FilmReadDto;
import com.yandex.filmorate.v2.dto.GenreReadDto;
import com.yandex.filmorate.v2.dto.MpaReadDto;
import com.yandex.filmorate.v2.entity.FilmEntity;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class FilmMapper {

    public FilmEntity toEntity(FilmCreateDto dto) {
        return new FilmEntity(
                null,
                dto.getName(),
                dto.getDescription(),
                dto.getReleaseDate(),
                dto.getDuration(),
                dto.getMpa().getId()
                );
    }

    public FilmReadDto toReadDto(FilmEntity entity, Set<Long> likes, Set<GenreReadDto> genres, MpaReadDto mpa) {
        return new FilmReadDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getReleaseDate(),
                entity.getDuration(),
                likes,
                genres,
                mpa
        );
    }
}
