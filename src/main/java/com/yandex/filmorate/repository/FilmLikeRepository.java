package com.yandex.filmorate.repository;

import com.yandex.filmorate.entity.FilmLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface FilmLikeRepository extends JpaRepository<FilmLikeEntity, Long> {

    @Query(value = """
        SELECT fl.userId 
        FROM FilmLikeEntity fl
        WHERE fl.filmId = :filmId
    """)
    Set<Long> getLikesByFilmId(Long filmId);

    void deleteByFilmIdAndUserId(Long filmId, Long userId);
}
