package com.yandex.filmorate.storage;


import com.yandex.filmorate.model.Film;
import com.yandex.filmorate.model.Film2;
import com.yandex.filmorate.model.User;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage{
    private static final Map<Long, Film> films = new HashMap<>();
    private static Long id = 1L;
    @Override
    public void addFilm(Film2 film) {
        film.setId(id);
        film.setLikes(new HashSet<>());
//        films.put(id++, film);
    }

    @Override
    public void deleteFilm(Long id) {
        films.remove(id);
    }

    @Override
    public Film2 updateFilm(Film2 film) {
//        films.replace(film.getId(),film);
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

    @Override
    public void addLike(Film film, User user) {
        film.getLikes().add(user.getId());
    }

    @Override
    public void deleteLike(Film film, User user) {
        film.getLikes().remove(user.getId());
    }

    @Override
    public List<Film> getTopFilms(int count) {
       return films.values().stream().sorted((f1, f2) -> {
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
    }
}
