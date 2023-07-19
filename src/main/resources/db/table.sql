CREATE TABLE `users` (
                         `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                         `user_id` VARCHAR(15) NOT NULL UNIQUE,
                         `user_name` VARCHAR(10) NOT NULL,
                         `password` VARCHAR(72) NOT NULL,
                         `email` VARCHAR(30) NOT NULL,
                         `nick_name` VARCHAR(10) NOT NULL UNIQUE,
                         `user_status` TINYINT(1) DEFAULT TRUE NOT NULL,
                         `user_role` VARCHAR(20) DEFAULT 'beginner' NOT NULL,
                         `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                         `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE `post` (
                        `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                        `user_id` BIGINT NOT NULL,
                        `title` VARCHAR(255) NOT NULL,
                        `content` TEXT NOT NULL,
                        `blind_state` BOOLEAN DEFAULT false,
                        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                        `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE `comment` (
                           `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                           `user_id` BIGINT NOT NULL,
                           `content` VARCHAR(50) NOT NULL,
                           `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                           `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           `post_id` BIGINT, -- post_id 컬럼 추가
                           FOREIGN KEY (`post_id`) REFERENCES `post`(`id`) -- post_id 외래키 추가
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE `reply` (
                         `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                         `comment_id` BIGINT NOT NULL,
                         `user_id` BIGINT NOT NULL,
                         `content` VARCHAR(50) NOT NULL,
                         `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (`comment_id`) REFERENCES `comment`(`id`)
) DEFAULT CHARSET=utf8mb4;