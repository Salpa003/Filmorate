package com.yandex.filmorate.service;

import com.yandex.filmorate.entity.Film;
import com.yandex.filmorate.exception.FilmValidateException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FilmServiceTest {

    @Autowired
    private FilmService filmService;

    @Test
    void addFilm() {
        Film film = Film.builder()
                .description("123")
                .name("123")
                .duration(120)
                .releaseDate(LocalDate.now())
                .build();

        filmService.addFilm(film);
        filmService.deleteFilm(film.getId());

        Assertions.assertThat(film.getId()).isNotNull();
    }

    @Test
    void validateName() {
        Film film1 = Film.builder()
                .description("123")
                .duration(120)
                .releaseDate(LocalDate.now())
                .build();
        Film film2 = Film.builder()
                .description("123")
                .name("")
                .duration(120)
                .releaseDate(LocalDate.now())
                .build();

        Assertions.assertThatThrownBy(()-> filmService.validate(film1)).isInstanceOf(FilmValidateException.class)
                .hasMessageContaining("name dont correct!");
        Assertions.assertThatThrownBy(()-> filmService.validate(film2)).isInstanceOf(FilmValidateException.class)
                .hasMessageContaining("name dont correct!");
    }

    @Test
    void validateDescriptionLength() {
        Film film = Film.builder()
                .description("""
                        11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
                        11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
                        11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
                        11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
                        11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
                        11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
                        11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
                        11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
                        11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
                        """)
                .name("123")
                .duration(120)
                .releaseDate(LocalDate.now())
                .build();

        Assertions.assertThatThrownBy(()-> filmService.validate(film)).isInstanceOf(FilmValidateException.class)
                .hasMessageContaining("description length gt 200!");
    }

    @Test
    void validateReleaseDate() {
        Film film = Film.builder()
                .name("123")
                .description("123")
                .duration(120)
                .releaseDate(LocalDate.of(1000,1,1))
                .build();
        Assertions.assertThatThrownBy(()-> filmService.validate(film)).isInstanceOf(FilmValidateException.class)
                .hasMessageContaining("release date dont correct!");
    }

    @Test
    void validateDuration() {
        Film film1 = Film.builder()
                .name("123")
                .description("123")
                .duration(0)
                .releaseDate(LocalDate.now())
                .build();
        Film film2 = Film.builder()
                .name("123")
                .description("123")
                .duration(-1)
                .releaseDate(LocalDate.now())
                .build();
        Assertions.assertThatThrownBy(()-> filmService.validate(film1)).isInstanceOf(FilmValidateException.class)
                .hasMessageContaining("duration dont correct!");
        Assertions.assertThatThrownBy(()-> filmService.validate(film2)).isInstanceOf(FilmValidateException.class)
                .hasMessageContaining("duration dont correct!");
    }

    @Test
    void updateFilm() {
        Film film = Film.builder()
                .name("123")
                .description("123")
                .duration(120)
                .releaseDate(LocalDate.now())
                .build();

        filmService.addFilm(film);
        film.setName("test");
        filmService.updateFilm(film);
        Film film2 = filmService.getFilmById(film.getId());
        filmService.deleteFilm(film.getId());

        Assertions.assertThat(film2.getName()).isEqualTo(film.getName());
    }

    @Test
    void getAllFilms() {
        Film film1 = Film.builder()
                .name("123")
                .description("123")
                .duration(120)
                .releaseDate(LocalDate.now())
                .build();
        Film film2 = Film.builder()
                .name("123")
                .description("123")
                .duration(120)
                .releaseDate(LocalDate.now())
                .build();

        filmService.addFilm(film1);
        filmService.addFilm(film2);
        Set<Film> films = filmService.getAllFilms();
        filmService.deleteFilm(film1.getId());
        filmService.deleteFilm(film2.getId());

        Assertions.assertThat(films).contains(film1, film2);
    }


// TODO
// протестировать контроллеры
// Потом проверить через Postman
}
