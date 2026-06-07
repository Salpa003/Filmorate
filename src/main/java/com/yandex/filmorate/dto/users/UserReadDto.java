package com.yandex.filmorate.dto.users;

import com.yandex.filmorate.entity.User;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Value
public class UserReadDto {
     Long id;

     String email;

     String login;

     String name;

     LocalDate birthday;

     Set<Long> friends;

     public UserReadDto(User user, Set<Long> friends) {
         this.id = user.getId();
         this.email = user.getEmail();
         this.login = user.getLogin();
         this.name = user.getName();
         this.birthday = user.getBirthday();
         this.friends = friends;
     }
}
