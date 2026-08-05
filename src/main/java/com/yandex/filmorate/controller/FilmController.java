package com.yandex.filmorate.controller;

import com.yandex.filmorate.service.FilmService;
import com.yandex.filmorate.dto.FilmReadDto;
import com.yandex.filmorate.dto.FilmCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private FilmService filmService;

    @GetMapping
    public List<FilmReadDto> getAllFilms() {
        return filmService.getAllFilms();
    }

    @DeleteMapping("/{id}/like/{userId}")
    public FilmReadDto deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.deleteLike(id, userId);
    }

    @PutMapping("/{id}/like/{userId}")
    public FilmReadDto addLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.addLike(id, userId);
    }

    @PostMapping
    public FilmReadDto addFilm(@RequestBody FilmCreateDto film) {
        return filmService.addFilm(film);
    }

    @PutMapping
    public FilmReadDto updateFilm(@RequestBody FilmReadDto film) {
        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public FilmReadDto getFilmById(@PathVariable("id") Long id) {
        return filmService.getFilmById(id);
    }

    @GetMapping("/popular")
    public List<FilmReadDto> getPopularFilms(@RequestParam(required = false) Integer count) {
        return filmService.getTopFilms(count == null ? 10 : count);
    }

}
