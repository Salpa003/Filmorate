package com.yandex.filmorate.p1.storage;


import com.yandex.filmorate.p1.model.Film;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage{
    private static final Map<Long, Film> films = new HashMap<>();
    private static Long id = 1L;
    @Override
    public void addFilm(Film film) {
        film.setId(id);
        film.setLikes(new HashSet<>());
        films.put(id++, film);
    }

    @Override
    public void deleteFilm(Long id) {
        films.remove(id);
    }

    @Override
    public Film updateFilm(Film film) {
        films.replace(film.getId(),film);
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        return films.values().stream().collect(Collectors.toList());
    }

    @Override
    public Film getFilmById(Long id) {
        return films.get(id);
    }

    @Override
    public boolean isExist(Long id) {
        return films.containsKey(id);
    }
}
