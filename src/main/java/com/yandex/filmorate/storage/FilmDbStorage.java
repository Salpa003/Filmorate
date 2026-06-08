package com.yandex.filmorate.storage;

import com.yandex.filmorate.repository.FilmRepository;
import com.yandex.filmorate.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilmDbStorage implements FilmStorage {

    @Autowired
    private FilmRepository filmRepository;
    @Override
    public void addFilm(Film film) {
        filmRepository.save(film);
    }

    @Override
    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }

    @Override
    public void updateFilm(Film film) {
        filmRepository.save(film);
    }

    @Override
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    @Override
    public Film getFilmById(Long id) {
        return filmRepository.findById(id).get();
    }

    @Override
    public boolean isExist(Long id) {
        return filmRepository.existsById(id);
    }
}
