package com.yandex.filmorate.service;

import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.model.Film;
import com.yandex.filmorate.model.User;
import com.yandex.filmorate.storage.FilmStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    @Autowired
    private FilmStorage filmStorage;

    @Autowired
    private UserService userService;

    public Film addLike(Long filmId, Long userId) {
        User user = userService.getUserById(userId);
        Film film = filmStorage.getFilmById(filmId);
        if (user == null || film == null)
            throw new NotFoundException("");
        film.getLikes().add(userId);
        return film;
    }

    public void deleteLike(Film film, Long userId) {
        User user = userService.getUserById(userId);
        if (film == null || user == null)
            throw new NotFoundException("");
        film.getLikes().remove(userId);
    }

    public List<Film> getTopFilms(Long count) {
        List<Film> set = filmStorage.getAllFilms().stream()
                .sorted((f1, f2) -> {
                    Set<Long> s1 = f1.getLikes();
                    Set<Long> s2 = f2.getLikes();
                    if (s1 == null)
                        s1 = new HashSet<>();
                    if (s2 == null)
                        s2 = new HashSet<>();
                    return Integer.compare(s2.size(), s1.size());
                })
                .limit(count)
                .collect(Collectors.toList());
        if (set == null)
            set = new ArrayList<>();
        return set;
    }

    public void addFilm(Film film) {
        filmStorage.addFilm(film);
    }

    public void deleteFilm(Long id) {
        filmStorage.deleteFilm(id);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
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


