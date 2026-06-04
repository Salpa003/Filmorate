package com.yandex.filmorate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "films_likes")
public class FilmsLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "film_id")
    private Long filmId;

    @Column(name = "user_id")
    private Long userId;

    public FilmsLikes(Long filmId, Long userId) {
        this.filmId = filmId;
        this.userId = userId;
    }
}
