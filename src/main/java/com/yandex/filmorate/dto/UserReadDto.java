package com.yandex.filmorate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserReadDto {
     Long id;
     String email;
     String login;
     String  name;
     LocalDate birthday;
     Set<Long> friends;
}
