package com.yandex.filmorate.dto;

import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UserCreateDto {
    String email;
    String login;
    String  name;
    LocalDate birthday;
    Set<Long> friends;
}
