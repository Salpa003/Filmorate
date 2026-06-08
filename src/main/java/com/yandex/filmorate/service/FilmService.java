package com.yandex.filmorate.service;

import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.model.Film;
import com.yandex.filmorate.model.User;
import com.yandex.filmorate.repository.FilmRepository;
import com.yandex.filmorate.storage.FilmStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    @Autowired
    @Qualifier("filmDbStorage")
    private FilmStorage filmStorage;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UserService userService;

    public Film addLike(Long filmId, Long userId) {
        User user = userService.getUserById(userId);
        Film film = filmStorage.getFilmById(filmId);
        if (user == null || film == null)
            throw new NotFoundException("");
        film.getLikes().add(user);
        filmRepository.save(film);
        return film;
    }

    public void deleteLike(Film film, Long userId) {
        User user = userService.getUserById(userId);
        if (film == null || user == null)
            throw new NotFoundException("");
        film.getLikes().remove(userId);
        filmRepository.save(film);
    }

    public List<Film> getTopFilms(Integer count) {
        List<Long> topFilmsId = filmRepository.getTopFilms(count);
        return topFilmsId.stream().map(filmStorage::getFilmById).collect(Collectors.toList());
    }

    public void addFilm(Film film) {
        filmStorage.addFilm(film);
    }

    public void deleteFilm(Long id) {
        filmStorage.deleteFilm(id);
    }

    public Film updateFilm(Film film) {
        filmStorage.updateFilm(film);
        return film;
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(Long id) {
        return filmStorage.getFilmById(id);
    }

    public boolean isExist(Long filmId) {
        return filmStorage.isExist(filmId);
    }

}


