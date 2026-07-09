--liquibase formatted sql

--changeset salpa:1
CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(300),
    login    VARCHAR(300),
    name     VARCHAR(300),
    birthday DATE
);

--changeset salpa:2
CREATE TABLE users_friends
(
    id        BIGSERIAL PRIMARY KEY,
    user_id   BIGINT REFERENCES users (id) NOT NULL,
    friend_id BIGINT REFERENCES users (id) NOT NULL,
    UNIQUE (user_id, friend_id)
);

--changeset salpa:3
CREATE INDEX idx_users_friends_user_id ON users_friends (user_id);

--changeset salpa:4
CREATE TABLE genres
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

--changeset salpa:5
CREATE TABLE rating
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(10) UNIQUE NOT NULL
);

--changeset salpa:6
CREATE TABLE films
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(255),
    description  TEXT,
    release_date DATE,
    duration     INT,
    rating       VARCHAR(30) REFERENCES rating (name)
);

--changeset salpa:7
CREATE TABLE films_likes
(
    id      BIGSERIAL PRIMARY KEY,
    film_id BIGINT REFERENCES films (id),
    user_id BIGINT REFERENCES users (id),
    UNIQUE (film_id, user_id)
);

--changeset salpa:8
CREATE TABLE films_genres
(
    id       BIGSERIAL PRIMARY KEY,
    film_id  BIGINT REFERENCES films (id),
    genre_id BIGINT REFERENCES genres (id),
    UNIQUE (film_id, genre_id)
);

-- changeset salpa:9
INSERT INTO rating(name)
VALUES ('0+'),
       ('6+'),
       ('12+'),
       ('16+'),
       ('18+');

--changeset salpa:10
INSERT INTO genres(name)
VALUES ('Комедия'),
       ('Драма'),
       ('Боевик'),
       ('Фантастика'),
       ('Документальный'),
       ('Ужасы');
