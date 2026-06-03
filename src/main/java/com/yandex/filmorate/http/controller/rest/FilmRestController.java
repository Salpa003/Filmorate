package com.yandex.filmorate.http.controller.rest;

import com.yandex.filmorate.entity.Film;
import com.yandex.filmorate.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmRestController {

    @Autowired
    private FilmService filmService;

    @PostMapping
    public Film addNewFilm(@RequestBody Film film) {
        filmService.addFilm(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        filmService.updateFilm(film);
        return film;
    }

    @GetMapping
    public Set<Film> getAllFilms() {
        return filmService.getAllFilms();
    }
}
