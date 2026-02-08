CREATE TABLE users
(
    id       VARCHAR(36) DEFAULT RANDOM_UUID() NOT NULL,
    username VARCHAR(100)                      NOT NULL UNIQUE,
    password VARCHAR(255)                      NOT NULL,
    email    VARCHAR(150)                      NOT NULL UNIQUE,
    role     VARCHAR(50)                       NOT NULL

);