package com.yandex.filmorate.v2.repository;

import com.yandex.filmorate.v2.entity.FilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilmRepository extends JpaRepository<FilmEntity, Long> {

    @Query("""
        SELECT f.id 
        FROM FilmEntity f
        INNER JOIN FilmLikeEntity fl ON f.id = fl.filmId
        GROUP BY f.id
        ORDER BY count(f.id) DESC
        LIMIT :c
    """)
    List<Long> getTopFilmsIds(Integer c);
}
