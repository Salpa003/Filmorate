package com.yandex.filmorate.model.db;

import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {
}
