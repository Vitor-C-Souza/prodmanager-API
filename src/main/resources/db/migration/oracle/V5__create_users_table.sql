CREATE TABLE users
(
    id       VARCHAR2(36) DEFAULT SYS_GUID() NOT NULL,
    username VARCHAR2(100)                   NOT NULL,
    password VARCHAR2(255)                   NOT NULL,
    email    VARCHAR2(150)                   NOT NULL,
    role     VARCHAR2(50)                    NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uk_users_username UNIQUE (username),
    CONSTRAINT uk_users_email UNIQUE (email)
);