package com.yandex.filmorate.storage;



import com.yandex.filmorate.model.Film;
import com.yandex.filmorate.model.User;

import java.util.List;

public interface FilmStorage {
    void addFilm(Film film);

    void deleteFilm(Long id);

    Film updateFilm(Film film);

    List<Film> getAllFilms();

    Film getFilmById(Long id);

    boolean isExist(Long id);

    void addLike(Film film, User user);
    void deleteLike(Film film, User user);

    List<Film> getTopFilms(int count);
}