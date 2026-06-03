package com.yandex.filmorate.repository;

import com.yandex.filmorate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
