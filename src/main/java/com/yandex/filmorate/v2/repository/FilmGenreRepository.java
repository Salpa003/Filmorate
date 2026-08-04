package com.yandex.filmorate.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface FilmGenreRepository extends JpaRepository<com.yandex.filmorate.v2.entity.FilmGenreEntity, Long> {

    @Query(value = """
    SELECT g.name FROM FilmGenreEntity fg
    INNER JOIN GenreEntity g ON fg.genreId = g.id
    WHERE fg.filmId = :filmId
    """)
    Set<String> getGenresByFilmId(Long filmId);
}
