package com.yandex.filmorate.repository;

import com.yandex.filmorate.entity.FilmGenreEntity;
import com.yandex.filmorate.entity.GenreEntity;
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

    @Query("""
                 SELECT g 
                 FROM GenreEntity g
                 INNER JOIN FilmGenreEntity  fg ON fg.genreId = g.id
                 WHERE fg.filmId = :filmId
            """)
    Set<GenreEntity> getGenresEntityByFilmId(Long filmId);
}
