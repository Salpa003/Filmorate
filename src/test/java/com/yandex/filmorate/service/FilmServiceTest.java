//package com.yandex.filmorate.service;
//
//import com.yandex.filmorate.annotaion.TestWithRollback;
//import com.yandex.filmorate.exception.NotFoundException;
//import com.yandex.filmorate.model.Film;
//import com.yandex.filmorate.model.User;
//import jakarta.transaction.Transactional;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Set;
//
//@SpringBootTest
//public class FilmServiceTest {
//    @Autowired
//    private FilmService filmService;
//
//    @Autowired
//    private UserService userService;
//
//    @TestWithRollback
//    void addFilm() {
//        Film expected =createFilm("film1");
//
//        filmService.addFilm(expected);
//        Film actual = filmService.getFilmById(expected.getId());
//
//        Assertions.assertThat(actual).isEqualTo(expected);
//    }
//
//    @TestWithRollback
//    void deleteFilm() {
//        Film film =createFilm("film1");
//        filmService.addFilm(film);
//
//        Film film2 = filmService.getFilmById(film.getId());
//        filmService.deleteFilm(film.getId());
//        Film film3 = filmService.getFilmById(film.getId());
//
//        Assertions.assertThat(film2).isEqualTo(film);
//        Assertions.assertThat(film3).isNull();
//    }
//
//    @TestWithRollback
//    void updateFilm() {
//        Film expected =createFilm("film1");
//        filmService.addFilm(expected);
//        expected.setName("filmHihihihaL:)");
//
//        filmService.updateFilm(expected);
//        Film actual = filmService.getFilmById(expected.getId());
//
//        Assertions.assertThat(actual).isEqualTo(expected);
//    }
//
//    @TestWithRollback
//    void getAllFilms() {
//        Film film1 =createFilm("film1");
//        Film film2 =createFilm("film1");
//        filmService.addFilm(film1);
//        filmService.addFilm(film2);
//
//        List<Film> films = filmService.getAllFilms();
//
//        Assertions.assertThat(films).hasSize(2).containsAll(List.of(film1,film2));
//    }
//
//    @TestWithRollback
//    void existFilm() {
//        Film film1 =createFilm("film1");
//
//        boolean exist1 = filmService.isExist(-10L);
//        filmService.addFilm(film1);
//        boolean exist2 = filmService.isExist(film1.getId());
//
//        Assertions.assertThat(exist1).isFalse();
//        Assertions.assertThat(exist2).isTrue();
//    }
//
//    @TestWithRollback
//    void getTopFilms() {
//        User user1 = createUser("User1");
//        User user2 = createUser("User2");
//        User user3 = createUser("User3");
//        userService.addUser(user1);
//        userService.addUser(user2);
//        userService.addUser(user3);
//        Film film1 = createFilm("Film1");
//        Film film2 = createFilm("Film2");
//        Film film3 = createFilm("Film3");
//        filmService.addFilm(film1);
//        filmService.addFilm(film2);
//        filmService.addFilm(film3);
//
//        filmService.addLike(film1.getId(), user1.getId());
//        filmService.addLike(film1.getId(), user2.getId());
//
//        filmService.addLike(film2.getId(), user1.getId());
//        filmService.addLike(film2.getId(), user2.getId());
//        filmService.addLike(film2.getId(), user3.getId());
//
//        filmService.addLike(film3.getId(), user1.getId());
//
//        List<Film> films = filmService.getTopFilms(3);
//        film1 = filmService.getFilmById(film1.getId());
//        film2 = filmService.getFilmById(film2.getId());
//        film3 = filmService.getFilmById(film3.getId());
//        Assertions.assertThat(films).hasSize(3).isEqualTo(List.of(film2, film1, film3));
//    }
//
//    @TestWithRollback
//    void deleteLike() {
//        User user1 = createUser("User1");
//        userService.addUser(user1);
//        Film film1 = createFilm("Film1");
//        filmService.addFilm(film1);
//        filmService.addLike(film1.getId(), user1.getId());
//
//        Film film2 = filmService.getFilmById(film1.getId());
//        filmService.deleteLike(film1.getId(), user1.getId());
//        Film film3 = filmService.getFilmById(film1.getId());
//
//        Assertions.assertThat(film2.getLikes()).contains(user1.getId());
//        Assertions.assertThat(film3.getLikes()).isEmpty();
//    }
//
//    @TestWithRollback
//    void deleteLikeWithException() {
//        User user1 = createUser("User1");
//        userService.addUser(user1);
//        Film film1 = createFilm("Film1");
//        filmService.addFilm(film1);
//        filmService.addLike(film1.getId(), user1.getId());
//
//        Assertions.assertThatThrownBy(() -> filmService.deleteLike(-10L, user1.getId())).isInstanceOf(NotFoundException.class);
//        Assertions.assertThatThrownBy(() -> filmService.deleteLike(-film1.getId(), -10L)).isInstanceOf(NotFoundException.class);
//    }
//
//    @TestWithRollback
//    void addLikeWithException() {
//        User user1 = createUser("User1");
//        userService.addUser(user1);
//        Film film1 = createFilm("Film1");
//        filmService.addFilm(film1);
//
//        Assertions.assertThatThrownBy(() -> filmService.addLike(-10L, user1.getId())).isInstanceOf(NotFoundException.class);
//        Assertions.assertThatThrownBy(() -> filmService.addLike(-film1.getId(), -10L)).isInstanceOf(NotFoundException.class);
//    }
//
//
//    private Film createFilm(String name) {
//        return Film.builder()
//                .name(name)
//                .description("description")
//                .releaseDate(LocalDate.now())
//                .duration(123)
//                .rating("A+")
//                .likes(Set.of())
//                .genre(Set.of())
//                .build();
//    }
//
//    private User createUser(String name) {
//        return User.builder()
//                .name(name)
//                .email("123@gmail.com")
//                .login("login1")
//                .friends(Set.of())
//                .birthday(LocalDate.now())
//                .build();
//    }
//}
