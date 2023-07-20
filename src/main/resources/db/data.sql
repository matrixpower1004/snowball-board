INSERT INTO user_tb (id, user_account, user_name, password, email, nick_name, user_status, user_role, created_at, updated_at)
VALUES (1, 'user1', 'User One', 'password123', 'user1@example.com', 'userone', TRUE, 'ROLE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO post (user_id, title, content, blind_state, created_at, updated_at)
VALUES (1, '게시물 제목', '게시물 내용입니다.', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO comment (user_id, post_id, content, created_at, updated_at)
VALUES (1, 1, '첫 번째 댓글입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO reply (user_id, comment_id, content, created_at, updated_at)
VALUES (1, 1, '첫 번째 댓글에 대한 첫 번째 답글입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
