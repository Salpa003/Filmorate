package com.yandex.filmorate.service;


import com.yandex.filmorate.dto.*;
import com.yandex.filmorate.entity.FilmEntity;
import com.yandex.filmorate.entity.FilmGenreEntity;
import com.yandex.filmorate.entity.FilmLikeEntity;
import com.yandex.filmorate.entity.RatingEntity;
import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.exception.ValidationException;
import com.yandex.filmorate.mapper.FilmMapper;
import com.yandex.filmorate.mapper.GenreMapper;
import com.yandex.filmorate.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    @Autowired
    private UserService userService;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmLikeRepository filmLikeRepository;

    @Autowired
    private FilmGenreRepository filmGenreRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private FilmMapper filmMapper;

    @Autowired
    private GenreMapper genreMapper;

    @Transactional
    public FilmReadDto addLike(Long filmId, Long userId) {
        if (!userService.isExist(userId) || !isExist(filmId))
            throw new NotFoundException("Film/User id not exist (FilmService:55)");
        FilmLikeEntity entity = new FilmLikeEntity(filmId, userId);
        filmLikeRepository.save(entity);
        return getFilmById(filmId);
    }

    @Transactional
    public FilmReadDto deleteLike(Long filmId, Long userId) {
        if (!userService.isExist(userId) || !isExist(filmId))
            throw new NotFoundException("Film/User id not exist (FilmService:64)");
        filmLikeRepository.deleteByFilmIdAndUserId(filmId, userId);
        return getFilmById(filmId);
    }

    @Transactional(readOnly = true)
    public List<FilmReadDto> getTopFilms(Integer count) {
        List<Long> ids = filmRepository.getTopFilmsIds(count);
        List<FilmReadDto> films = new ArrayList<>();
        for (Long id : ids) {
            films.add(getFilmById(id));
        }
        return films;
    }

    @Transactional
    public FilmReadDto addFilm(FilmCreateDto film) {
        validateFilm(film);
        FilmEntity entity = filmMapper.toEntity(film);
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

        log.info("Add new film ({})", film);

        return getFilmById(entity.getId());
    }


    @Transactional
    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }

    @Transactional
    public FilmReadDto updateFilm(FilmReadDto film) {
        if (film == null || !isExist(film.getId())) {
            throw new NotFoundException("Update unknown film (FilmService:113)");
        }

        FilmEntity entity = filmMapper.toEntity(film);
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

        log.info("Update film ({})", film);

        return film;
    }

    @Transactional(readOnly = true)
    public List<FilmReadDto> getAllFilms() {
        List<Long> ids = filmRepository.findAllIds();
        List<FilmReadDto> filmReadDtos = new ArrayList<>();
        for (Long id : ids) {
            filmReadDtos.add(getFilmById(id));
        }
        return filmReadDtos;
    }

    @Transactional(readOnly = true)
    public FilmReadDto getFilmById(Long id) {
        FilmEntity entity = filmRepository.findById(id).get();
        Set<Long> likes = filmLikeRepository.getLikesByFilmId(id);
        List<GenreReadDto> genres = filmGenreRepository.getGenresEntityByFilmId(id)
                .stream()
                .map(genreMapper::toReadDto)
                .sorted((g1, g2) -> g1.getId() - g2.getId())
                .collect(Collectors.toList());
        RatingEntity ratingEntity = ratingRepository.findById(entity.getRating()).get();
        FilmReadDto dto = filmMapper.toReadDto(entity, likes, genres, new MpaReadDto(ratingEntity.getId(), ratingEntity.getName()));
        return dto;
    }

    @Transactional(readOnly = true)
    public boolean isExist(Long filmId) {
        return filmRepository.existsById(filmId);
    }


    private static final int DESCRIPTION_LENGTH_MAX = 200;
    private static final LocalDate DATE_FILM_START = LocalDate.of(1895, 12, 28);

    @Transactional(readOnly = true)
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

        if (film.getMpa() != null) {
            if (!ratingRepository.existsById(film.getMpa().getId())) {
                throw new NotFoundException("Mpa unknown");
            }
        }
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            for (GenreReadDto g : film.getGenres()) {
                if (!genreRepository.existsById(g.getId())) {
                    throw new NotFoundException("");
                }
            }
        }

        if (message != null) {
            log.info("Ошибка при валидации фильма, {}", message);
            throw new ValidationException(message);
        }
    }
}


