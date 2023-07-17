INSERT INTO users (user_id, user_name, email, nick_name, password, user_status, user_role, created_at, updated_at)
VALUES ('user1', 'User One', 'user1@example.com', 'userone', 'password123', TRUE, 'ROLE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO post (user_id, title, content, blind_state, created_at, updated_at)
VALUES (2, '게시물 제목', '게시물 내용입니다.', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO comment (user_id, post_id, content, created_at, updated_at)
VALUES (1, 1, '첫 번째 댓글입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO reply (user_id, comment_id, content, created_at, updated_at)
VALUES (1, 1, '첫 번째 댓글에 대한 첫 번째 답글입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);