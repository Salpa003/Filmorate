package com.yandex.filmorate.service;


import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.model.Film;
import com.yandex.filmorate.model.User;
import com.yandex.filmorate.storage.FilmStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    @Autowired
//    @Qualifier("inMemoryFilmStorage")
    @Qualifier("dbFilmStorage")
    private FilmStorage filmStorage;

    @Autowired
    private UserService userService;

    @Transactional
    public void addLike(Long filmId, Long userId) {
        User user = userService.getUserById(userId);
        Film film = getFilmById(filmId);
        if (user == null || film == null)
            throw new NotFoundException("");
        filmStorage.addLike(film, user);
    }

    @Transactional
    public void deleteLike(Long filmId, Long userId) {
        User user = userService.getUserById(userId);
        Film film = getFilmById(filmId);
        if (film == null || user == null)
            throw new NotFoundException("");
        filmStorage.deleteLike(film, user);
    }

    @Transactional(readOnly = true)
    public List<Film> getTopFilms(Integer count) {
        return filmStorage.getTopFilms(count);
    }

    @Transactional
    public void addFilm(Film film) {
        filmStorage.addFilm(film);
    }

    @Transactional
    public void deleteFilm(Long id) {
        filmStorage.deleteFilm(id);
    }

    @Transactional
    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    @Transactional(readOnly = true)
    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }
    @Transactional(readOnly = true)
    public Film getFilmById(Long id) {
        return filmStorage.getFilmById(id);
    }
    @Transactional(readOnly = true)
    public boolean isExist(Long filmId) {
        return filmStorage.isExist(filmId);
    }

}


