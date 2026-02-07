CREATE TABLE users
(
    id       VARCHAR(36) DEFAULT RANDOM_UUID() NOT NULL,
    username VARCHAR(100)                      NOT NULL,
    password VARCHAR(255)                      NOT NULL,
    email    VARCHAR(150)                      NOT NULL,
    role     VARCHAR(50)                       NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uk_users_username UNIQUE (username),
    CONSTRAINT uk_users_email UNIQUE (email)
);