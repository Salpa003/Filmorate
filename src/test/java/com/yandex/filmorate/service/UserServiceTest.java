package com.yandex.filmorate.service;

import com.yandex.filmorate.entity.User;
import com.yandex.filmorate.exception.UserValidateException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void saveNewUser() {
        User user = User.builder()
                .login("test")
                .birthday(LocalDate.of(2026,1,1))
                .email("ad@")
                .build();

        userService.addUser(user);

        Assertions.assertThat(user.getId()).isNotNull();
        userService.deleteUser(user.getId());
    }
    @Test
    void saveNewUserWithEmailError() {
        User user1 = User.builder()
                .login("test")
                .birthday(LocalDate.of(2026,1,1))
                .email("ad")
                .build();
        User user2 = User.builder()
                .login("test")
                .birthday(LocalDate.of(2026,1,1))
                .email("")
                .build();
        User user3 = User.builder()
                .login("test")
                .birthday(LocalDate.of(2026,1,1))
                .email(" ")
                .build();

        Assertions.assertThatThrownBy(()-> userService.addUser(user1)).isInstanceOf(UserValidateException.class);
        Assertions.assertThatThrownBy(()-> userService.addUser(user2)).isInstanceOf(UserValidateException.class);
        Assertions.assertThatThrownBy(()-> userService.addUser(user3)).isInstanceOf(UserValidateException.class);
    }
    @Test
    void saveNewUserWithLoginError() {
        User user1 = User.builder()
                .login("test test")
                .birthday(LocalDate.of(2026,1,1))
                .email("arr@gmail.com")
                .build();
        User user2 = User.builder()
                .login("")
                .birthday(LocalDate.of(2026,1,1))
                .email("arr@gmail.com")
                .build();

        Assertions.assertThatThrownBy(()-> userService.addUser(user1)).isInstanceOf(UserValidateException.class);
        Assertions.assertThatThrownBy(()-> userService.addUser(user2)).isInstanceOf(UserValidateException.class);
    }

    @Test
    void saveNewUserWithBirthdayError() {
        User user1 = User.builder()
                .login("test")
                .birthday(LocalDate.of(2100,1,1))
                .email("arr@gmail.com")
                .build();

        Assertions.assertThatThrownBy(()-> userService.addUser(user1)).isInstanceOf(UserValidateException.class);
    }

    @Test
    void updateUser() {
        User user1 = User.builder()
                .login("test")
                .birthday(LocalDate.of(2000,1,1))
                .email("arr@gmail.com")
                .build();
        userService.addUser(user1);

        user1.setName("test2");
        userService.updateUser(user1);
        User user2 = userService.getUserById(user1.getId());

        Assertions.assertThat(user2.getName()).isEqualTo(user1.getName());
        userService.deleteUser(user2.getId());
    }

    @Test
    void getAllUsers() {
        User user1 = User.builder()
                .login("test")
                .birthday(LocalDate.of(2000,1,1))
                .email("arr@gmail.com")
                .build();
        User user2 = User.builder()
                .login("test2")
                .birthday(LocalDate.of(2000,1,1))
                .email("arr@gmail.com")
                .build();

        userService.addUser(user1);
        userService.addUser(user2);
        Set<User> users = userService.getAllUsers();
        userService.deleteUser(user1.getId());
        userService.deleteUser(user2.getId());

        Assertions.assertThat(users).contains(user1,user2);

    }

}
