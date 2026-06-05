package com.yandex.filmorate.p1.storage;


import com.yandex.filmorate.p1.model.Film;

import java.util.List;

public interface FilmStorage {
    void addFilm(Film film);

    void deleteFilm(Long id);

    Film updateFilm(Film film);

    List<Film> getAllFilms();

    Film getFilmById(Long id);

    boolean isExist(Long id);
}