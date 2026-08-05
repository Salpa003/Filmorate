package com.yandex.filmorate.controller;

import com.yandex.filmorate.service.FilmService;
import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.dto.FilmReadDto;
import com.yandex.filmorate.dto.FilmCreateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    @Autowired
    private FilmService filmService;

    @GetMapping
    public List<FilmReadDto> getAllFilms() {
        return filmService.getAllFilms();
    }

    @DeleteMapping("/{id}/like/{userId}")
    public FilmReadDto deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(id, userId);
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public FilmReadDto addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
        return filmService.getFilmById(id);
    }

    @PostMapping
    public FilmReadDto addFilm(@RequestBody FilmCreateDto film) {
        Long id = filmService.addFilm(film);
        log.info("Add new film ({})", film);
        return filmService.getFilmById(id);
    }

    @PutMapping
    public FilmReadDto updateFilm(@RequestBody FilmReadDto film) {
        if (film == null || !filmService.isExist(film.getId())) {
            throw new NotFoundException("Not found my");
        }
        FilmReadDto film1 = filmService.updateFilm(film);
        log.info("Update film ({})", film);
        return film1;
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
