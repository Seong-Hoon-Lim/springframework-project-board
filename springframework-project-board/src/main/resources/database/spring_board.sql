USE spring_board;
CREATE TABLE spring_board.member
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `memberId`   VARCHAR(50)  NOT NULL,
    `password`   VARCHAR(16)  NOT NULL,
    `name`       VARCHAR(50)  NOT NULL,
    `gender`     VARCHAR(4)   NOT NULL,
    `birth`      VARCHAR(10)  NOT NULL,
    `email`      VARCHAR(50)  NOT NULL,
    `phone`      VARCHAR(13)  NOT NULL,
    `zipcode`    VARCHAR(5)   NOT NULL,
    `addr1`      VARCHAR(100) NOT NULL,
    `addr2`      VARCHAR(100) NOT NULL,
    `created_at` DATETIME     NOT NULL DEFAULT now(),
    `updated_at` DATETIME     NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
);

CREATE TABLE spring_board.board
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT,
    `member_id`    BIGINT       NOT NULL,
    `member_name`  VARCHAR(50)  NOT NULL,
    `title`        VARCHAR(255) NOT NULL,
    `content`      TEXT         NOT NULL,
    `hit`          INT          NULL     DEFAULT 0,
    `ip`           VARCHAR(20)  NOT NULL,
    `ripple_count` INT          NULL     DEFAULT 0,
    `file_name`    VARCHAR(100) NULL     DEFAULT NULL,
    `file_size`    BIGINT       NULL     DEFAULT 0,
    `created_at`   DATETIME     NOT NULL DEFAULT now(),
    `updated_at`   DATETIME     NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`member_id`) REFERENCES spring_board.`member` (`id`)
        ON DELETE CASCADE
) DEFAULT CHARSET = UTF8;

CREATE TABLE spring_board.ripple
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `board_id`    BIGINT      NOT NULL,
    `member_id`   BIGINT      NOT NULL,
    `member_name` VARCHAR(10) NOT NULL,
    `content`     TEXT        NOT NULL,
    `ip`          VARCHAR(20),
    `created_at`  DATETIME    NOT NULL DEFAULT now(),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`board_id`) REFERENCES spring_board.board (`id`)
        ON DELETE CASCADE,
    FOREIGN KEY (`member_id`) REFERENCES spring_board.`member` (`id`)
        ON DELETE CASCADE
) DEFAULT CHARSET = UTF8;
