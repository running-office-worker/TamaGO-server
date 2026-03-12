CREATE TABLE IF NOT EXISTS t_fcm_token
(
    fcm_token_id BIGINT AUTO_INCREMENT NOT NULL,
    created_at   datetime(6) NULL,
    updated_at   datetime(6) NULL,
    deleted_at   datetime(6) NULL,
    user_id      BIGINT NOT NULL,
    token        VARCHAR(255) NULL,
    device_type  enum ('IOS') NULL,
    last_used_at datetime(6) NULL,
    CONSTRAINT pk_t_fcm_token PRIMARY KEY (fcm_token_id)
);

CREATE TABLE IF NOT EXISTS t_letters
(
    letter_id  BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime(6) NULL,
    updated_at datetime(6) NULL,
    deleted_at datetime(6) NULL,
    title      VARCHAR(255) NULL,
    content    TEXT NULL,
    mood       enum ('ENCOURAGE', 'PRAISE', 'PROUD', 'SAD', 'WORRY') NULL,
    CONSTRAINT pk_t_letters PRIMARY KEY (letter_id)
);

CREATE TABLE IF NOT EXISTS t_monsters
(
    monster_id      BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime(6) NULL,
    updated_at      datetime(6) NULL,
    deleted_at      datetime(6) NULL,
    next_monster_id BIGINT NULL,
    CONSTRAINT pk_t_monsters PRIMARY KEY (monster_id)
);

CREATE TABLE IF NOT EXISTS t_notifications
(
    notification_id BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime(6) NULL,
    updated_at      datetime(6) NULL,
    deleted_at      datetime(6) NULL,
    title           VARCHAR(255) NULL,
    content         VARCHAR(255) NULL,
    is_read         BIT NULL,
    user_id         BIGINT NOT NULL,
    scheduled_at    datetime(6) NULL,
    CONSTRAINT pk_t_notifications PRIMARY KEY (notification_id)
);

CREATE TABLE IF NOT EXISTS t_received_letters
(
    received_letter_id BIGINT AUTO_INCREMENT NOT NULL,
    created_at         datetime(6) NULL,
    updated_at         datetime(6) NULL,
    deleted_at         datetime(6) NULL,
    user_id            BIGINT NOT NULL,
    letter_id          BIGINT NOT NULL,
    read_status        enum ('READ', 'UNREAD') NULL,
    CONSTRAINT pk_t_received_letters PRIMARY KEY (received_letter_id)
);

CREATE INDEX FK_received_letters_letter_id ON t_received_letters (letter_id);

CREATE TABLE IF NOT EXISTS t_refresh_token
(
    refresh_token_id BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime(6) NULL,
    updated_at       datetime(6) NULL,
    deleted_at       datetime(6) NULL,
    user_id          BIGINT NOT NULL,
    token            VARCHAR(255) NULL,
    CONSTRAINT pk_t_refresh_token PRIMARY KEY (refresh_token_id)
);

CREATE TABLE IF NOT EXISTS t_running
(
    running_id  BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime(6) NULL,
    updated_at  datetime(6) NULL,
    deleted_at  datetime(6) NULL,
    pace        DOUBLE NULL,
    time        time(6) NULL,
    kcal        INT NULL,
    kilometre   DOUBLE NULL,
    started_at  datetime(6) NULL,
    finished_at datetime(6) NULL,
    user_id     BIGINT NOT NULL,
    CONSTRAINT pk_t_running PRIMARY KEY (running_id)
);

CREATE TABLE IF NOT EXISTS t_users
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    datetime(6) NULL,
    updated_at    datetime(6) NULL,
    deleted_at    datetime(6) NULL,
    nickname      VARCHAR(255) NULL,
    `role`        enum ('ADMIN', 'USER') NOT NULL,
    goal_kilo     INT NULL,
    total_kilo    INT NULL,
    last_login_at datetime(6) NULL,
    CONSTRAINT pk_t_users PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS t_user_auth
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    email       VARCHAR(255) NULL,
    provider    VARCHAR(255) NULL,
    password    VARCHAR(255) NULL,
    external_id VARCHAR(255) NULL,
    user_id     BIGINT NOT NULL,
    CONSTRAINT pk_t_user_auth PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS t_user_monsters
(
    user_monster_id BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime(6) NULL,
    updated_at      datetime(6) NULL,
    deleted_at      datetime(6) NULL,
    monster_id      BIGINT NOT NULL,
    user_id         BIGINT NOT NULL,
    CONSTRAINT pk_t_user_monsters PRIMARY KEY (user_monster_id)
);

ALTER TABLE t_monsters
    ADD CONSTRAINT FKh0bjivbxisexq3nblihda15jb FOREIGN KEY (next_monster_id) REFERENCES t_monsters (monster_id);

ALTER TABLE t_user_auth
    ADD CONSTRAINT FKdesjd2qt6rq1159ct3ojf02c FOREIGN KEY (user_id) REFERENCES t_users (id);

ALTER TABLE t_user_monsters
    ADD CONSTRAINT FKqetmjhl5k7s5r9jfvsftilyhm FOREIGN KEY (monster_id) REFERENCES t_monsters (monster_id);
