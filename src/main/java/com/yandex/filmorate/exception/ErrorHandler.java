//package com.yandex.filmorate.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.Map;
//
//@RestControllerAdvice
//public class ErrorHandler {
//
//    @ExceptionHandler
//    @ResponseStatus
//    public Map<String,String> handle(final RuntimeException exception) {
//        return Map.of("error", exception.getMessage());
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map<String,String> handle(final UserValidateException exception) {
//        return Map.of("error", exception.getMessage());
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map<String,String> handle(final FilmValidateException exception) {
//        return Map.of("error", exception.getMessage());
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public Map<String,String> handle(final NotFoundException exception) {
//        return Map.of("error", exception.getMessage());
//    }
//
//}
