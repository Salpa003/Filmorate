package com.yandex.filmorate.v2.dto;

import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

@Value
public class FilmReadDto {
     Long id;
     String name;
     String description;
     LocalDate releaseDate;
     Integer duration;
     Set<Long> likes;
     Set<GenreReadDto> genres;
     MpaReadDto mpa;
}
