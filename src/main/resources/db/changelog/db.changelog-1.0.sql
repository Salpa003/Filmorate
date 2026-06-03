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
