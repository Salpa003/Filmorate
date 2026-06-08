package com.yandex.filmorate.repository;

import com.yandex.filmorate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
