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
CREATE INDEX idx_users_friends_user_id ON users_friends(user_id);

--changeset salpa:4
CREATE TABLE genre
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

--changeset salpa:5
CREATE TABLE film
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255),
    description TEXT,
    release_date DATE,
    duration    INT,
    rating      VARCHAR(30)
);

--changeset salpa:6
CREATE TABLE films_likes
(
    film_id BIGINT REFERENCES film (id),
    user_id BIGINT REFERENCES users (id),
    PRIMARY KEY (film_id, user_id)
);

--changeset salpa:7
CREATE TABLE films_genres
(
    film_id BIGINT REFERENCES film (id),
    genre_id BIGINT REFERENCES genre(id),
    PRIMARY KEY (film_id, genre_id)
);