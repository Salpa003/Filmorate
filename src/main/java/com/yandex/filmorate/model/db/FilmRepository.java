package com.yandex.filmorate.model.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilmRepository extends JpaRepository<FilmEntity, Long> {

    @Query(value = """
        SELECT fe 
        FROM FilmEntity fe
        INNER JOIN FilmLikeEntity fl ON fl.filmId = fe.id
        GROUP BY fe.id
        ORDER BY count(*) DESC 
        LIMIT :c
    """)
    List<FilmEntity> getTopFilms(Integer c);
}
