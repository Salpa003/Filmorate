package com.yandex.filmorate.v2.controller;

import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.exception.ValidationException;
import com.yandex.filmorate.model.Film;
import com.yandex.filmorate.model.Film2;
import com.yandex.filmorate.v2.service.FilmService;
import com.yandex.filmorate.v2.dto.FilmCreateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
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
    public Film2 addFilm(@RequestBody FilmCreateDto film) {
        filmService.addFilm(film);
        log.info("Add new film ({})", film);
        return film;
    }

    @PutMapping
    public Film2 updateFilm(@RequestBody Film2 film) {
        if (film == null || !filmService.isExist(film.getId())) {
            throw new NotFoundException("Not found my");
        }
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

}
