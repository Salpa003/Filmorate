package com.yandex.filmorate.v2.service;


import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.exception.ValidationException;
import com.yandex.filmorate.model.Film;
import com.yandex.filmorate.model.Film2;
import com.yandex.filmorate.model.User;
import com.yandex.filmorate.service.UserService;
import com.yandex.filmorate.v2.dto.FilmReadDto;
import com.yandex.filmorate.v2.dto.GenreReadDto;
import com.yandex.filmorate.v2.mapper.FilmMapper;
import com.yandex.filmorate.v2.dto.FilmCreateDto;
import com.yandex.filmorate.v2.entity.*;
import com.yandex.filmorate.v2.repository.FilmGenreRepository;
import com.yandex.filmorate.v2.repository.FilmLikeRepository;
import com.yandex.filmorate.v2.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FilmService {



//    @Autowired
////    @Qualifier("inMemoryFilmStorage")
//    @Qualifier("dbFilmStorage")
//    private FilmStorage filmStorage;
//
    @Autowired
    private UserService userService;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmLikeRepository filmLikeRepository;

    @Autowired
    private FilmGenreRepository filmGenreRepository;

    @Autowired
    private FilmMapper mapper;

    @Transactional
    public void addLike(Long filmId, Long userId) {
        User user = userService.getUserById(userId);
        Film film = getFilmById(filmId);
        if (user == null || film == null)
            throw new NotFoundException("");
        FilmLikeEntity entity = new FilmLikeEntity(filmId, userId);
       filmLikeRepository.save(entity);
    }

    @Transactional
    public void deleteLike(Long filmId, Long userId) {
        User user = userService.getUserById(userId);
        Film film = getFilmById(filmId);
        if (film == null || user == null)
            throw new NotFoundException("");
       filmLikeRepository.deleteByFilmIdAndUserId(filmId, userId);
    }

    @Transactional(readOnly = true)
    public List<FilmReadDto> getTopFilms(Integer count) {
        List<Long> ids = filmRepository.getTopFilmsIds(count);
        List<FilmReadDto> films = new ArrayList<>();
        for (Long id: ids) {
            films.add(getFilmById(id));
        }
        return films;
    }

    @Transactional
    public void addFilm(FilmCreateDto film) {
        validateFilm(film);
        FilmEntity entity = mapper.toEntity(film);
        filmRepository.save(entity);

        if (film.getLikes() != null && !film.getLikes().isEmpty()) {
            film.getLikes()
                    .forEach((uid) -> {
                        FilmLikeEntity likeEntity = new FilmLikeEntity(entity.getId(), uid);
                        filmLikeRepository.save(likeEntity);
                    });
        }
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
           film.getGenres().forEach(dto -> {
               FilmGenreEntity genreEntity = new FilmGenreEntity(entity.getId(), dto.getId());
               filmGenreRepository.save(genreEntity);
           });
        }
    }


    @Transactional
    public void deleteFilm(Long id) {
       filmRepository.deleteById(id);
    }

    @Transactional
    public Film2  updateFilm(Film2 film) {
        return filmStorage.updateFilm(film);
    }

    @Transactional(readOnly = true)
    public List<FilmReadDto> getAllFilms() {
        filmRepository.findAll();
        return null;
    }
    @Transactional(readOnly = true)
    public FilmReadDto getFilmById(Long id) {
        FilmEntity entity = filmRepository.findById(id).get();
        Set<Long> likes = filmLikeRepository.getLikesByFilmId(id);
        Set<GenreReadDto> genres = filmGenreRepository.
        FilmReadDto dto = mapper.toReadDto(entity,likes, );
        return dto;
    }
    @Transactional(readOnly = true)
    public boolean isExist(Long filmId) {
        return filmRepository.existsById(filmId);
    }


    private static final int DESCRIPTION_LENGTH_MAX = 200;
    private static final LocalDate DATE_FILM_START = LocalDate.of(1895,12, 28);

    public void validateFilm(FilmCreateDto film) {
        String message = null;
        if (film.getName().isBlank())
            message = "Название фильма не может быть пустым";
        if (film.getDescription().length() > DESCRIPTION_LENGTH_MAX)
            message = "У фильма слишком большое описание. Нужно не больше " + DESCRIPTION_LENGTH_MAX + " символов";
        if (film.getReleaseDate().isBefore(DATE_FILM_START))
            message = "Фильм вышел до того как придымали фильмы";
        if (film.getDuration() <= 0)
            message = "Продолжительность должна быть положительной";

        if (message != null) {
//            log.info("Ошибка при валидации фильма, {}",message);
            throw new ValidationException(message);
        }
    }
}


