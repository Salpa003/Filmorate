package com.yandex.filmorate.storage;


import com.yandex.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    void addFilm(Film film);

    void deleteFilm(Long id);

    void updateFilm(Film film);

    List<Film> getAllFilms();

    Film getFilmById(Long id);

    boolean isExist(Long id);
}