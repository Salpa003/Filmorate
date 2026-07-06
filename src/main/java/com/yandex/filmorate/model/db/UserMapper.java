package com.yandex.filmorate.model.db;

import com.yandex.filmorate.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserMapper {

   public UserEntity map(User user) {
    return new UserEntity(
            user.getId(),
            user.getEmail(),
            user.getLogin(),
            user.getName(),
            user.getBirthday());
   }

   public User unmap(UserEntity user, Set<Long> friendsId) {
       return new User( user.getId(),
               user.getEmail(),
               user.getLogin(),
               user.getName(),
               user.getBirthday(),
               friendsId);
   }
}
