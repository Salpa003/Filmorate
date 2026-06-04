package com.yandex.filmorate.service;


import com.yandex.filmorate.entity.Film;
import com.yandex.filmorate.exception.FilmValidateException;
import com.yandex.filmorate.repository.FilmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    public void addFilm(Film film) {
        validate(film);
        filmRepository.save(film);
    }

    public void updateFilm(Film film) {
        validate(film);
        filmRepository.save(film);
    }

    public Film getFilmById(Long id) {
        return filmRepository.findById(id).get();
    }

    public Set<Film> getAllFilms() {
        return filmRepository.findAll().stream().collect(Collectors.toSet());
    }

    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }

    private final LocalDate FIRST_FILM_RELEASE = LocalDate.of(1895, 12, 28);

    public void validate(Film film) {
        String errorMessage = null;
        if (film.getName() == null || film.getName().isBlank())
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
