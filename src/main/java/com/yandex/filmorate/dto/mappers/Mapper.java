package com.yandex.filmorate.dto.mappers;

public interface Mapper<F,T> {

    T map(F f);

    F unmap(T t);
}
