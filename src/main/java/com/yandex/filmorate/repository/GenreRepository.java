package com.yandex.filmorate.repository;

import com.yandex.filmorate.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenreRepository extends JpaRepository<GenreEntity,Integer> {

    @Query(value = """
     SELECT ge.id 
     FROM GenreEntity ge
     WHERE ge.name = :name
    """)
    Integer findByName(String name);

    boolean existsByName(String name);
}
