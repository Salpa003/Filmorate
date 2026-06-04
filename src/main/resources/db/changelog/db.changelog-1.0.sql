--liquibase formatted sql

--changeset salpa:1
CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(128),
    login VARCHAR(128),
    name VARCHAR(128),
    birthday DATE
);

--changeset sapla:2
CREATE TABLE films(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(128),
    description TEXT,
    release_date DATE,
    duration INT
);

--changeset salpa:3
CREATE TABLE users_friends(
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT REFERENCES users(id) NOT NULL ,
    friend_id BIGINT REFERENCES  users(id) NOT NULL
);

--changeset salpa:4
CREATE TABLE films_likes(
    id BIGSERIAL PRIMARY KEY,
    film_id BIGINT REFERENCES films(id),
    user_id BIGINT REFERENCES users(id)
);
