package com.yandex.filmorate.annotaion;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Test
@Transactional
@Rollback
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestWithRollback {
}
