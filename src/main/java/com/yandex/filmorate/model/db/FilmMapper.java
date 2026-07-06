package com.yandex.filmorate.model.db;

import com.yandex.filmorate.model.Film;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class FilmMapper {

    public FilmEntity map(Film film) {
        return new FilmEntity(
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRating()
                );
    }

    public Film unmap(FilmEntity film, Set<Long> likes, Set<String> genres) {
        return new Film(
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                likes,
                genres,
                film.getRating()
        );
    }
}
