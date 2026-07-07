package com.yandex.filmorate.model.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenreRepository extends JpaRepository<GenreEntity,Long> {

    @Query(value = """
     SELECT ge.id 
     FROM GenreEntity ge
     WHERE ge.name = :name
    """)
    Long findByName(String name);

    boolean existsByName(String name);
}
