package com.yandex.filmorate.v2.dto;

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
//{
//        "name": "New film",
//        "description": "Description for film 29",
//        "releaseDate": "2000-01-01",
//        "duration": 90,
//        "genres": [
//        {
//        "id": 4
//        }
//        ],
//        "mpa": {
//        "id": 3
//        }
//        }
