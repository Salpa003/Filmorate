package com.yandex.filmorate.repository;

import com.yandex.filmorate.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
