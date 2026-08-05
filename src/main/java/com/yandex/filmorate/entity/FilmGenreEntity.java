package com.yandex.filmorate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "films_genres")
public class FilmGenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private Long filmId;

    private Integer genreId;

    public FilmGenreEntity(Long filmId, Integer genreId) {
        this.filmId = filmId;
        this.genreId = genreId;
    }
}
