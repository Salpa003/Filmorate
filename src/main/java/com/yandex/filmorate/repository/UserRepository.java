package com.yandex.filmorate.repository;

import com.yandex.filmorate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
