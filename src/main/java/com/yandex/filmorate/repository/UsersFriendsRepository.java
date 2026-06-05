//package com.yandex.filmorate.repository;
//
//import com.yandex.filmorate.entity.User;
//import com.yandex.filmorate.entity.UsersFriends;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.Set;
//
//public interface UsersFriendsRepository extends JpaRepository<UsersFriends, Long> {
//
//    void removeByUserIdAndFriendId(Long userId, Long friendId);
//
//    @Query("SELECT uf.friendId FROM UsersFriends uf WHERE uf.userId=:userId")
//    Set<Long> getFriends(Long userId);
//}
