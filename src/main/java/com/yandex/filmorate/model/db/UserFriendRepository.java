package com.yandex.filmorate.model.db;

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
