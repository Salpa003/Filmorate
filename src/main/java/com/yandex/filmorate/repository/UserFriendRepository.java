package com.yandex.filmorate.repository;

import com.yandex.filmorate.entity.UsersFriendsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface UserFriendRepository extends JpaRepository<UsersFriendsEntity, Long> {

    void deleteByUserIdAndFriendId(Long userId, Long friendI);

    @Query(value = """
       SELECT uf.friendId FROM UsersFriendsEntity uf
       WHERE uf.userId = :userId
    """)
    Set<Long> findFriendsByUserId(Long userId);
}
