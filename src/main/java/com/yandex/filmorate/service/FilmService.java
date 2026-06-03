package com.yandex.filmorate.service;


import com.yandex.filmorate.entity.Film;
import com.yandex.filmorate.exception.FilmValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
@Slf4j
public class FilmService {

    public void addFilm(Film film) {

    }

    public void updateFilm(Film film) {

    }

    public Set<Film> getAllFilms() {
        return null;
    }

    private final LocalDate FIRST_FILM_RELEASE = LocalDate.of(1895, 12, 28);

    public void validate(Film film) {
        String errorMessage = null;
        if (film.getName().isBlank())
            errorMessage = "name dont correct!";
        else if (film.getDescription().length() > 200)
            errorMessage = "description length gt 200!";
        else if (film.getReleaseDate().isBefore(FIRST_FILM_RELEASE))
            errorMessage = "release date dont correct!";
        else if (film.getDuration() <= 0)
            errorMessage = "duration dont correct!";

        if (errorMessage != null) {
            log.warn("Validate error {} {}", film,errorMessage);
            throw new FilmValidateException(errorMessage);
        }
    }
}
