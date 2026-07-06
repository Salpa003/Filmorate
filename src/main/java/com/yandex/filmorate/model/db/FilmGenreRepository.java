package com.yandex.filmorate.model.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface FilmGenreRepository extends JpaRepository<FilmGenreEntity, Long> {

    @Query(value = """
    SELECT g.name FROM FilmGenreEntity fg
    INNER JOIN GenreEntity g ON fg.genreId = g.id
    WHERE fg.filmId = :filmId
    """)
    Set<String> getGenresByFilmId(Long filmId);
}
