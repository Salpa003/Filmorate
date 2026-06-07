package com.yandex.filmorate.exception;

import lombok.Getter;

@Getter
public class UserValidateException extends RuntimeException {
    private final String message;
    public UserValidateException(String message) {
        super(message);
        this.message = message;
    }
}
