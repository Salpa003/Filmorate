package com.yandex.filmorate.dto;

import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Value
public class FilmReadDto {
     Long id;
     String name;
     String description;
     LocalDate releaseDate;
     Integer duration;
     Set<Long> likes;
     List<GenreReadDto> genres;
     MpaReadDto mpa;
}
