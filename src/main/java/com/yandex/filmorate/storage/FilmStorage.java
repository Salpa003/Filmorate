package com.yandex.filmorate.storage;



import com.yandex.filmorate.model.Film;
import com.yandex.filmorate.model.Film2;
import com.yandex.filmorate.model.User;

import java.util.List;

public interface FilmStorage {
    void addFilm(Film2 film);

    void deleteFilm(Long id);

    Film2 updateFilm(Film2 film);

    List<Film> getAllFilms();

    Film getFilmById(Long id);

    boolean isExist(Long id);

    void addLike(Film film, User user);
    void deleteLike(Film film, User user);

    List<Film> getTopFilms(int count);
}