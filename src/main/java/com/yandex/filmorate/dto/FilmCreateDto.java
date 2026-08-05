package com.yandex.filmorate.dto;

import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

@Value
public class FilmCreateDto {
    String name;
    String description;
    LocalDate releaseDate;
    Integer duration;
    Set<Long> likes;
    Set<GenreReadDto> genres;
    MpaReadDto mpa;
}
