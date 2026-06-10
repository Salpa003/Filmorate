package com.yandex.filmorate.service;

import com.yandex.filmorate.exception.NotFoundException;
import com.yandex.filmorate.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void addUser() {
        User expected = createNewUser("test1");

        userService.addUser(expected);
        User actual = userService.getUserById(expected.getId());
        userService.deleteUser(actual.getId());

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateUser() {
        User expected = createNewUser("test2");
        userService.addUser(expected);

        expected.setName("Exex");
        userService.updateUser(expected);
        User actual = userService.getUserById(expected.getId());
        userService.deleteUser(actual.getId());

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateUnknownUser() {
        User user = User.builder()
                .id(-10L)
                .build();

        Assertions.assertThatThrownBy(() -> userService.updateUser(user)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void deleteUnknownUser() {
        Assertions.assertThatThrownBy(() -> userService.deleteUser(-10L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void getUnknownUser() {
        Assertions.assertThatThrownBy(() -> userService.getUserById(-10L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void getAllUsers() {
        User user1 = createNewUser("test1");
        User user2 = createNewUser("test2");
        userService.addUser(user1);
        userService.addUser(user2);

        List<User> allUsers = userService.getAllUsers();
        userService.deleteUser(user1.getId());
        userService.deleteUser(user2.getId());

        Assertions.assertThat(allUsers).hasSize(2).containsAll(List.of(user1, user2));
    }

    @Test
    void exist() {
        User user = createNewUser("test1");
        userService.addUser(user);

        boolean yes = userService.isExist(user.getId());
        userService.deleteUser(user.getId());
        boolean no = userService.isExist(user.getId());

        Assertions.assertThat(no).isFalse();
        Assertions.assertThat(yes).isTrue();
    }

    @Test
    void addFriend() {
        User user1 = createNewUser("test1");
        User user2 = createNewUser("test1");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addFriend(user1.getId(), user2.getId());

        Set<User> friends1 = userService.getFriends(user1.getId());
        Set<User> friends2 = userService.getFriends(user2.getId());
        userService.deleteFriend(user1.getId(), user2.getId());
        Set<User> friends21 = userService.getFriends(user1.getId());
        Set<User> friends22 = userService.getFriends(user2.getId());
        userService.deleteUser(user1.getId());
        userService.deleteUser(user2.getId());

        Assertions.assertThat(friends1).hasSize(1).contains(user2);
        Assertions.assertThat(friends2).hasSize(1).contains(user1);
        Assertions.assertThat(friends21).isEmpty();
        Assertions.assertThat(friends22).isEmpty();
    }

    @Test
    void addUnknownFriend() {
        User user1 = createNewUser("test1");
        userService.addUser(user1);

        Assertions.assertThatThrownBy(() -> userService.addFriend(user1.getId(), -10L)).isInstanceOf(NotFoundException.class);
        Assertions.assertThatThrownBy(() -> userService.addFriend(-10L, user1.getId())).isInstanceOf(NotFoundException.class);
        userService.deleteUser(user1.getId());
    }

    @Test
    void deleteUnknownFriend() {
        User user1 = createNewUser("test1");
        userService.addUser(user1);

        Assertions.assertThatThrownBy(() -> userService.deleteFriend(user1.getId(), -10L)).isInstanceOf(NotFoundException.class);
        Assertions.assertThatThrownBy(() -> userService.deleteFriend(-10L, user1.getId())).isInstanceOf(NotFoundException.class);
        userService.deleteUser(user1.getId());
    }

    @Test
    void getUnknownUserFriends() {
        Assertions.assertThatThrownBy(() -> userService.getFriends(-10L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void getDoubleFriends() {
        User user1 = createNewUser("test1");
        User user2 = createNewUser("test2");
        User user3 = createNewUser("test3");
        User user4 = createNewUser("test4");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.addUser(user4);
        userService.addFriend(user1.getId(), user2.getId());
        userService.addFriend(user1.getId(), user3.getId());
        userService.addFriend(user4.getId(), user2.getId());
        userService.addFriend(user4.getId(), user3.getId());

        Set<User> friends = userService.getDoubleFriends(user1.getId(), user4.getId());

        Assertions.assertThat(friends).hasSize(2).contains(user2, user3);
        userService.deleteFriend(user1.getId(), user2.getId());
        userService.deleteFriend(user1.getId(), user3.getId());
        userService.deleteFriend(user4.getId(), user2.getId());
        userService.deleteFriend(user4.getId(), user3.getId());
        userService.deleteUser(user1.getId());
        userService.deleteUser(user2.getId());
        userService.deleteUser(user3.getId());
        userService.deleteUser(user4.getId());
    }

    @Test
    void getUnknownUsersDoubleFriends() {
        User user1 = createNewUser("test1");
        userService.addUser(user1);

        Assertions.assertThatThrownBy(() -> userService.getDoubleFriends(user1.getId(), -10L)).isInstanceOf(NotFoundException.class);
        Assertions.assertThatThrownBy(() -> userService.getDoubleFriends(-10L, user1.getId())).isInstanceOf(NotFoundException.class);
        userService.deleteUser(user1.getId());
    }

    private User createNewUser(String name) {
        return User.builder()
                .name(name)
                .films(new HashSet<>())
                .friends(new HashSet<>())
                .build();
    }
}
