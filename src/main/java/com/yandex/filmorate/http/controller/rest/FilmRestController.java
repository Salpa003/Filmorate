package com.yandex.filmorate.http.controller.rest;

import com.yandex.filmorate.entity.Film;
import com.yandex.filmorate.service.FilmService;
import jakarta.websocket.server.PathParam;
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

    @DeleteMapping("/{id}")
    public Film deleteFilm(@PathVariable Long id) {
        return filmService.deleteFilm(id);
    }

    @GetMapping
    public Set<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id,userId);
        return filmService.getFilmById(id);
    }
    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(id,userId);
        return filmService.getFilmById(id);
    }

    @GetMapping("/popular")
    public Set<Long> getPopularFilms(@RequestParam Long count) {
        if (count == null)
            count = 10L;
        return filmService.getTopFilms(count);
    }

}
