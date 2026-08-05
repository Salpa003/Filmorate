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
import com.yandex.filmorate.repository.FilmGenreRepository;
import com.yandex.filmorate.repository.FilmLikeRepository;
import com.yandex.filmorate.repository.FilmRepository;
import com.yandex.filmorate.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private FilmMapper filmMapper;

    @Autowired
    private GenreMapper genreMapper;

    @Transactional
    public void addLike(Long filmId, Long userId) {
        if (!userService.isExist(userId) ||!isExist(filmId))
            throw new NotFoundException("");
        FilmLikeEntity entity = new FilmLikeEntity(filmId, userId);
       filmLikeRepository.save(entity);
    }

    @Transactional
    public void deleteLike(Long filmId, Long userId) {
        if (!userService.isExist(userId) ||!isExist(filmId))
            throw new NotFoundException("");
       filmLikeRepository.deleteByFilmIdAndUserId(filmId, userId);
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
    public Long addFilm(FilmCreateDto film) {
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

        return entity.getId();
    }


    @Transactional
    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }

    @Transactional
    public FilmReadDto updateFilm(FilmReadDto film) {
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

        return film;
    }

    @Transactional(readOnly = true)
    public List<FilmReadDto> getAllFilms() {
        List<Long> ids = filmRepository.findAllIds();
        List<FilmReadDto> filmReadDtos = new ArrayList<>();
        for (Long id: ids) {
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
                .sorted((g1,g2)-> g1.getId() - g2.getId())
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
            if (film.getMpa().getId() < 1 || film.getMpa().getId() > 5) {
                throw new NotFoundException("Mpa not found");
            }
        }
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            for (GenreReadDto g: film.getGenres()) {
                if (g.getId() < 1 || g.getId() > 6) {
                    throw new NotFoundException("");
                }
            }
        }

        if (message != null) {
//            log.info("Ошибка при валидации фильма, {}",message);
            throw new ValidationException(message);
        }
    }
}


