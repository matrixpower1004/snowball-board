CREATE TABLE `user_tb`
(
    `id`           BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_account` VARCHAR(15)                           NOT NULL UNIQUE,
    `user_name`    VARCHAR(10)                           NOT NULL,
    `password`     VARCHAR(72)                           NOT NULL,
    `email`        VARCHAR(30)                           NOT NULL,
    `nick_name`    VARCHAR(10)                           NOT NULL UNIQUE,
    `user_status`  BOOLEAN     DEFAULT TRUE              NOT NULL,
    `user_role`    VARCHAR(20) DEFAULT 'BEGINNER'        NOT NULL,
    `created_at`   TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`   TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL
) DEFAULT CHARSET = utf8mb4;



-- post(게시판)
CREATE TABLE `post`
(
    `id`          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id`     BIGINT                              NOT NULL,
    `title`       VARCHAR(255)                        NOT NULL,
    `content`     TEXT                                NOT NULL,
    `blind_state` BOOLEAN   DEFAULT false, -- ture면 게시글 블라인드 상태
    `created_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user_tb (id)
) DEFAULT CHARSET = utf8mb4;


-- 게시글 신고 게시판. 하나의 게시글은 한 유저가 한 번만 신고할 수 있다.
create table `report_board`
(
    `id`             bigint primary key auto_increment,
    `post_id`        bigint       not null comment '신고 대상 게시글',
    `report_type`    varchar(20)  not null comment '신고 유형 : 욕설, 비방, 음란, 스팸,광고',
    `reporter_id`    bigint       not null comment '신고자 id',
    `report_img_url` varchar(255) not null comment '증거 스크린샷',
    `report_date`    timestamp    not null default now() comment '신고 일자',
    FOREIGN KEY (post_id) REFERENCES post (id),
    FOREIGN KEY (reporter_id) REFERENCES user_tb (id),
    UNIQUE (post_id, reporter_id)
) DEFAULT CHARSET = utf8mb4;


-- comment(댓글)
CREATE TABLE `comment`
(
    `id`         BIGINT PRIMARY KEY AUTO_INCREMENT,
    `post_id`    BIGINT                              NOT NULL,
    `user_id`    BIGINT                              NOT NULL,
    `content`    VARCHAR(50)                         NOT NULL,
    `created_at` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES post (id),
    FOREIGN KEY (user_id) REFERENCES user_tb (id)
) DEFAULT CHARSET = utf8mb4;


-- REPLY (대댓글)
CREATE TABLE `reply`
(
    `id`         BIGINT PRIMARY KEY AUTO_INCREMENT,
    `comment_id` BIGINT      NOT NULL,
    `user_id`    BIGINT      NOT NULL,
    `content`    VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (comment_id) REFERENCES comment(id),
    FOREIGN KEY (user_id) REFERENCES user_tb (id)
) DEFAULT CHARSET = utf8mb4;