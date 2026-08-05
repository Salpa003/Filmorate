package com.yandex.filmorate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "films_likes")
public class FilmLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long filmId;

    private Long userId;

    public FilmLikeEntity(Long filmId, Long userId) {
        this.filmId = filmId;
        this.userId = userId;
    }
}
