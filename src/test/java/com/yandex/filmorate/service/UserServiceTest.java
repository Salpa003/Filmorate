package com.yandex.filmorate.service;

import com.yandex.filmorate.entity.User;
import com.yandex.filmorate.exception.NotFoundException;
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
    void validateEmail() {
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

        Assertions.assertThatThrownBy(()-> userService.validate(user1)).isInstanceOf(UserValidateException.class)
                .hasMessageContaining("email dont correct!");
        Assertions.assertThatThrownBy(()-> userService.validate(user2)).isInstanceOf(UserValidateException.class)
                .hasMessageContaining("email dont correct!");
        Assertions.assertThatThrownBy(()-> userService.validate(user3)).isInstanceOf(UserValidateException.class)
                .hasMessageContaining("email dont correct!");
    }
    @Test
    void validateLogin() {
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

        Assertions.assertThatThrownBy(()-> userService.validate(user1)).isInstanceOf(UserValidateException.class)
                .hasMessageContaining("login dont correct!");
        Assertions.assertThatThrownBy(()-> userService.validate(user2)).isInstanceOf(UserValidateException.class)
                .hasMessageContaining("login dont correct!");
    }

    @Test
    void validateBirthday() {
        User user1 = User.builder()
                .login("test")
                .birthday(LocalDate.of(2100,1,1))
                .email("arr@gmail.com")
                .build();

        Assertions.assertThatThrownBy(()-> userService.validate(user1)).isInstanceOf(UserValidateException.class)
                .hasMessageContaining("birthday dont correct!");
    }

    @Test
    void deleteUnknownUser() {
        Assertions.assertThatThrownBy(()-> userService.deleteUser(-1L)).isInstanceOf(NotFoundException.class);
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
    void updateUnknownUser() {
        User user1 = User.builder()
                .id(-1L)
                .login("test")
                .birthday(LocalDate.of(2000,1,1))
                .email("arr@gmail.com")
                .build();


        Assertions.assertThatThrownBy(()-> userService.updateUser(user1)).isInstanceOf(NotFoundException.class);
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

    @Test
    void addFriend() {
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

        userService.addFriend(user1.getId(), user2.getId());

       Assertions.assertThat(userService.getFriends(user1.getId())).isEqualTo(Set.of(user2.getId()));
       Assertions.assertThat(userService.getFriends(user2.getId())).isEqualTo(Set.of(user1.getId()));

       userService.deleteFriend(user1.getId(), user2.getId());
        userService.deleteUser(user1.getId());
        userService.deleteUser(user2.getId());
    }

    @Test
    void addUnknownFriend() {
        Assertions.assertThatThrownBy(()-> userService.addFriend(-1L,2L)).isInstanceOf(NotFoundException.class);
    }
    @Test
    void deleteUnknownFriend() {
        Assertions.assertThatThrownBy(()-> userService.deleteFriend(-1L,2L)).isInstanceOf(NotFoundException.class);
    }
    @Test
    void getUnknownFriend() {
        Assertions.assertThatThrownBy(()-> userService.getFriends(-1L)).isInstanceOf(NotFoundException.class);
    }


    @Test
    void doubleFriends() {
        User user1 = User.builder()
                .login("test2")
                .birthday(LocalDate.of(2000,1,1))
                .email("arr@gmail.com")
                .build();
        User user2 = User.builder()
                .login("test2")
                .birthday(LocalDate.of(2000,1,1))
                .email("arr@gmail.com")
                .build();
        User user3 = User.builder()
                .login("test2")
                .birthday(LocalDate.of(2000,1,1))
                .email("arr@gmail.com")
                .build();
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        userService.addFriend(user1, user2);
        userService.addFriend(user3, user2);

        Set<User> users = userService.getDoubleFriends(user1, user3);
        Assertions.assertThat(users).isEqualTo(Set.of(user2));

        userService.deleteFriend(user1, user2);
        userService.deleteFriend(user3, user2);
        userService.deleteUser(user1.getId());
        userService.deleteUser(user2.getId());
        userService.deleteUser(user3.getId());
    }

}
