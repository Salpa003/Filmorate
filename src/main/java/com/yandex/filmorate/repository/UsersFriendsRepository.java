package com.yandex.filmorate.repository;

import com.yandex.filmorate.model.User;
import com.yandex.filmorate.model.UsersFriends;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersFriendsRepository extends JpaRepository<UsersFriends, Long> {

    void deleteByUserAndFriend(User user, User friend);

    void removeByUser_IdAndFriend_Id(Long user, Long friend);

    UsersFriends getByUser_Id(Long userId);
}
