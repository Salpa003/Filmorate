package com.yandex.filmorate.storage;

import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.model.Film;
import com.yandex.filmorate.model.Film2;
import com.yandex.filmorate.model.GenreView;
import com.yandex.filmorate.model.User;
import com.yandex.filmorate.model.db.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DbFilmStorage implements FilmStorage {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private FilmLikeRepository filmLikeRepository;

    @Autowired
    private FilmGenreRepository filmGenreRepository;

    @Autowired
    private FilmMapper mapper;

    @Override
    public void addFilm(Film2 film) {
        FilmEntity filmEntity = mapper.map(film);
        filmRepository.save(filmEntity);
        film.setId(filmEntity.getId());


        Set<Long> likes = film.getLikes();
        if (likes == null)
            film.setLikes(new HashSet<>());
        RatingEntity mpa = film.getMpa();
        mpa = ratingRepository.findById(mpa.getId()).orElse(null);
        film.setMpa(mpa);

        Set<GenreView> genres = film.getGenres();
        if (genres == null)
            throw new NotFoundException("Genre not found!");
        Set<Integer> ids = genres.stream().map(g -> g.getId()).collect(Collectors.toSet());
        for (Integer id: ids) {
            boolean b = genreRepository.existsById(id);
            if (!b)
                throw new NotFoundException("Genre not found!");
        }

//        List<Long> ids = genres.stream()
//                .map(name -> genreRepository.findByName(name)).toList();

        List<FilmGenreEntity> list = ids.stream()
                .map(genre -> new FilmGenreEntity(film.getId(), genre)).collect(Collectors.toList());
        filmGenreRepository.saveAll(list);
    }
    @Override
    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }

    @Override
    public Film2 updateFilm(Film2 film) {
//        FilmEntity filmEntity = mapper.map(film);
//        filmRepository.save(filmEntity);
        addFilm(film);
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        List<FilmEntity> fe = filmRepository.findAll();
        return fe.stream()
                .map(f -> mapper.unmap(f, getLikes(f.getId()), getGenres(f.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public Film getFilmById(Long id) {
        Optional<FilmEntity> maybeFilm = filmRepository.findById(id);
        return maybeFilm.map(film -> mapper.unmap(film, getLikes(id), getGenres(id))).orElse(null);
    }

    @Override
    public boolean isExist(Long id) {
        return filmRepository.existsById(id);
    }

    @Override
    public void addLike(Film film, User user) {
        FilmLikeEntity filmLikeEntity = new FilmLikeEntity(film.getId(), user.getId());
        filmLikeRepository.save(filmLikeEntity);
    }

    @Override
    public void deleteLike(Film film, User user) {
        filmLikeRepository.deleteByFilmIdAndUserId(film.getId(), user.getId());
    }

    @Override
    public List<Film> getTopFilms(int count) {
        List<FilmEntity> filmEntities = filmRepository.getTopFilms(count);
        return filmEntities.stream()
                .map(fe -> mapper.unmap(fe, getLikes(fe.getId()), getGenres(fe.getId())))
                .collect(Collectors.toList());
    }

    private Set<Long> getLikes(Long filmId) {
        return filmLikeRepository.getLikesByFilmId(filmId);
    }

    private Set<String> getGenres(Long filmId) {
        return filmGenreRepository.getGenresByFilmId(filmId);
    }
}
