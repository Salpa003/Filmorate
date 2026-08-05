package com.yandex.filmorate.mapper;

import com.yandex.filmorate.dto.FilmCreateDto;
import com.yandex.filmorate.dto.FilmReadDto;
import com.yandex.filmorate.entity.FilmEntity;
import com.yandex.filmorate.dto.GenreReadDto;
import com.yandex.filmorate.dto.MpaReadDto;
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

    public FilmEntity toEntity(FilmReadDto dto) {
        return new FilmEntity(
                dto.getId(),
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
