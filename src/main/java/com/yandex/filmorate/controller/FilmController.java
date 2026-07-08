package com.yandex.filmorate.controller;

import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.exception.ValidationException;
import com.yandex.filmorate.model.Film;
import com.yandex.filmorate.model.Film2;
import com.yandex.filmorate.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private static final int DESCRIPTION_LENGTH_MAX = 200;
    private static final LocalDate DATE_FILM_START = LocalDate.of(1895,12, 28);

    @Autowired
    private FilmService filmService;

    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }
    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(id,userId);
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
        return filmService.getFilmById(id);
    }

    @PostMapping
    public Film2 addFilm(@RequestBody Film2 film) {
        validateFilm(film);
        filmService.addFilm(film);
        log.info("Add new film ({})", film);
        return film;
    }

    @PutMapping
    public Film2 updateFilm(@RequestBody Film2 film) {
        if (film == null || !filmService.isExist(film.getId())) {
            throw new NotFoundException("Not found my");
        }
        validateFilm(film);
        Film2 film1 = filmService.updateFilm(film);
        log.info("Update film ({})", film);
        return film1;
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable("id") Long id) {
       return filmService.getFilmById(id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) Integer count) {
        return filmService.getTopFilms(count == null? 10 : count);
    }

    public void validateFilm(Film2 film) {
        String message = null;
        if (film.getName().isBlank())
            message = "Название фильма не может быть пустым";
        if (film.getDescription().length() > DESCRIPTION_LENGTH_MAX)
            message = "У фильма слишком большое описание. Нужно не больше " + DESCRIPTION_LENGTH_MAX + " символов";
        if (film.getReleaseDate().isBefore(DATE_FILM_START))
           message = "Фильм вышел до того как придымали фильмы";
        if (film.getDuration() <= 0)
            message = "Продолжительность должна быть положительной";

        if (message != null) {
            log.info("Ошибка при валидации фильма, {}",message);
            throw new ValidationException(message);
        }
    }
    public void validateFilm(Film film) {
        String message = null;
        if (film.getName().isBlank())
            message = "Название фильма не может быть пустым";
        if (film.getDescription().length() > DESCRIPTION_LENGTH_MAX)
            message = "У фильма слишком большое описание. Нужно не больше " + DESCRIPTION_LENGTH_MAX + " символов";
        if (film.getReleaseDate().isBefore(DATE_FILM_START))
            message = "Фильм вышел до того как придымали фильмы";
        if (film.getDuration() <= 0)
            message = "Продолжительность должна быть положительной";

        if (message != null) {
            log.info("Ошибка при валидации фильма, {}",message);
            throw new ValidationException(message);
        }
    }



}
