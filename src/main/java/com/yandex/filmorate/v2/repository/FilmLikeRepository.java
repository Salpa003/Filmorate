package com.yandex.filmorate.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface FilmLikeRepository extends JpaRepository<com.yandex.filmorate.v2.entity.FilmLikeEntity, Long> {

    @Query(value = """
        SELECT fl.userId 
        FROM FilmLikeEntity fl
        WHERE fl.filmId = :filmId
    """)
    Set<Long> getLikesByFilmId(Long filmId);

    void deleteByFilmIdAndUserId(Long filmId, Long userId);
}
