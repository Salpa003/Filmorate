package com.yandex.filmorate.repository;

import com.yandex.filmorate.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query(nativeQuery = true,value = """
            SELECT fl.film_id
            FROM filmorate.public.films_likes fl
            GROUP BY fl.film_id
            ORDER BY count(fl.user_id) DESC
            LIMIT :count
    """)
    List<Long> getTopFilms(Integer count);
}
