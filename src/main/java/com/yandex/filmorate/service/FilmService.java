package com.yandex.filmorate.service;


import com.yandex.filmorate.entity.Film;
import com.yandex.filmorate.entity.FilmsLikes;
import com.yandex.filmorate.entity.User;
import com.yandex.filmorate.exception.FilmValidateException;
import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.repository.FilmRepository;
import com.yandex.filmorate.repository.FilmsLikesRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmsLikesRepository filmsLikesRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void addFilm(Film film) {
        validate(film);
        filmRepository.save(film);
    }

    @Transactional
    public void updateFilm(Film film) {
        validate(film);
        Optional<Film> byId = filmRepository.findById(film.getId());
        if (byId.isEmpty())
            throw new NotFoundException("");
        filmRepository.save(film);
    }

    @Transactional
    public Film getFilmById(Long id) {
        return filmRepository.findById(id).get();
    }

    @Transactional
    public Set<Film> getAllFilms() {
        return filmRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Transactional
    public Film deleteFilm(Long id) {
        Optional<Film> byId = filmRepository.findById(id);
        if (byId.isEmpty())
            throw new NotFoundException("");
        filmRepository.deleteById(id);
        return byId.get();
    }

    @Transactional
    public void addLike(Long filmId, Long userId) {
        User user = userService.getUserById(userId);
        Optional<Film> byId = filmRepository.findById(filmId);
        if (user == null || byId.isEmpty())
            throw new NotFoundException("");
        FilmsLikes filmsLikes = new FilmsLikes(filmId,userId);
        filmsLikesRepository.save(filmsLikes);
    }

    @Transactional
    public void deleteLike(Long filmId, Long userId) {
        User user = userService.getUserById(userId);
        Optional<Film> byId = filmRepository.findById(filmId);
        if (user == null || byId.isEmpty())
            throw new NotFoundException("");
        filmsLikesRepository.removeByFilmIdAndUserId(filmId,userId);
    }

    @Transactional
    public Set<Long> getTopFilms(Long count) {
        return filmsLikesRepository.getTopFilms(count);
    }

    private final LocalDate FIRST_FILM_RELEASE = LocalDate.of(1895, 12, 28);

    public void validate(Film film) {
        String errorMessage = null;
        if (film.getName() == null || film.getName().isBlank()) {
            errorMessage = "name dont correct!";
        } else if (film.getDescription().length() > 200) {
            errorMessage = "description length gt 200!";
        } else if (film.getReleaseDate().isBefore(FIRST_FILM_RELEASE)) {
            errorMessage = "release date dont correct!";
        } else if (film.getDuration() <= 0) {
            errorMessage = "duration dont correct!";
        }

        if (errorMessage != null) {
            log.warn("Validate error {} {}", film, errorMessage);
            throw new FilmValidateException(errorMessage);
        }
    }

}
