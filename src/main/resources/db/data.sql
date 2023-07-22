
INSERT INTO user_tb (id, user_account, user_name, password, email, nick_name, user_status, user_role, created_at, updated_at)
VALUES
    (1, 'user1', 'User Two', 'password121', 'user1@example.com', 'userone', TRUE, 'ROLE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'user2', 'User Two', 'password123', 'user2@example.com', 'usertwo', TRUE, 'ROLE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'user3', 'User Three', 'password123', 'user3@example.com', 'userthree', TRUE, 'ROLE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 'user4', 'User Four', 'password123', 'user4@example.com', 'userfour', TRUE, 'ROLE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 'user5', 'User Five', 'password123', 'user5@example.com', 'userfive', TRUE, 'ROLE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO post (id, user_id, title, content, blind_state, created_at, updated_at)
VALUES
    (1, 1, '게시물 제목 2', '게시물 내용입니다. 2', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 2, '게시물 제목 2', '게시물 내용입니다. 2', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 3, '게시물 제목 3', '게시물 내용입니다. 3', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 4, '게시물 제목 4', '게시물 내용입니다. 4', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 5, '게시물 제목 5', '게시물 내용입니다. 5', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO comment (id, user_id, post_id, content, created_at, updated_at)
VALUES
    (1, 1, 1, '두 번째 댓글입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 2, 2, '두 번째 댓글입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 2, 3, '세 번째 댓글입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 4, 4, '네 번째 댓글입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 5, 5, '다섯 번째 댓글입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO reply (id, user_id, comment_id, content, created_at, updated_at)
VALUES
    (1, 1, 1, '두 번째 댓글에 대한 첫 번째 답글입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 2, 2, '두 번째 댓글에 대한 첫 번째 답글입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 2, 3, '세 번째 댓글에 대한 첫 번째 답글입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 4, 4, '네 번째 댓글에 대한 첫 번째 답글입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 5, 5, '다섯 번째 댓글에 대한 첫 번째 답글입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

