package com.snowball.board;

import com.snowball.board.domain.board.model.Post;
import com.snowball.board.domain.board.repository.PostRepository;
import com.snowball.board.domain.user.model.User;
import com.snowball.board.domain.user.repository.UserRepository;
import com.snowball.board.domain.board.model.Comment;
import com.snowball.board.domain.board.repository.CommentRepository;
import com.snowball.board.domain.board.model.Reply;
import com.snowball.board.domain.board.repository.ReplyRepository;
import com.snowball.board.common.util.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.time.Instant;

@SpringBootApplication
public class SnowballBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnowballBoardApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, ReplyRepository replyRepository) {
        return (args) -> {
            User user1 = userRepository.save(User.builder()
                    .userAccount("user1")
                    .userName("User One")
                    .password("password1")
                    .email("user1@email.com")
                    .nickName("user1nick")
                    .userStatus(true)
                    .userRole(UserRole.BEGINNER)
                    .build());

            User user2 = userRepository.save(User.builder()
                    .userAccount("user2")
                    .userName("User Two")
                    .password("password2")
                    .email("user2@email.com")
                    .nickName("user2nick")
                    .userStatus(true)
                    .userRole(UserRole.BEGINNER)
                    .build());

            Post post1 = postRepository.save(Post.builder()
                    .user(user1)
                    .title("1 게시판")
                    .content("댓글 추가")
                    .blindState(false)
                    .build());

            Post post2 = postRepository.save(Post.builder()
                    .user(user2)
                    .title("2번째 게시판")
                    .content("대댓글 추가")
                    .blindState(false)
                    .build());

            Comment comment1 = commentRepository.save(Comment.builder()
                    .user(user1)
                    .post(post1)
                    .content("첫 댓글입니다.")
                    .createdAt(Timestamp.from(Instant.now()))
                    .updatedAt(Timestamp.from(Instant.now()))
                    .build());

            Comment comment2 = commentRepository.save(Comment.builder()
                    .user(user2)
                    .post(post2)
                    .content("2 댓글입니다.")
                    .createdAt(Timestamp.from(Instant.now()))
                    .updatedAt(Timestamp.from(Instant.now()))
                    .build());

            replyRepository.save(Reply.builder()
                    .user(user1)
                    .comment(comment1)
                    .content("첫 대댓글입니다..")
                    .createdAt(Timestamp.from(Instant.now()))
                    .updatedAt(Timestamp.from(Instant.now()))
                    .build());

            replyRepository.save(Reply.builder()
                    .user(user2)
                    .comment(comment2)
                    .content("2 대댓글입니다.")
                    .createdAt(Timestamp.from(Instant.now()))
                    .updatedAt(Timestamp.from(Instant.now()))
                    .build());
        };
    }
}
