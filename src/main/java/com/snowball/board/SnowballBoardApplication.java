package com.snowball.board;

import com.snowball.board.common.util.UserRole;
import com.snowball.board.domain.board.emtity.Comment;
import com.snowball.board.domain.board.emtity.Post;
import com.snowball.board.domain.board.emtity.User;
import com.snowball.board.domain.board.repository.CommentRepository; // 추가: CommentRepository import
import com.snowball.board.domain.board.repository.PostRepository;
import com.snowball.board.domain.board.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;

@SpringBootApplication
public class SnowballBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnowballBoardApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDummyData(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) { // 추가: CommentRepository 매개변수로 추가
        return args -> {
            // 더미 사용자 생성
            User user1 = new User(null, "user1", true, UserRole.USER, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
            User user2 = new User(null, "user2", true, UserRole.USER, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
            userRepository.save(user1);
            userRepository.save(user2);

            // 더미 게시물 생성
            Post post1 = new Post(null, user1, "제목1", "내용1", false, UserRole.USER, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
            Post post2 = new Post(null, user2, "제목2", "내용2", false, UserRole.USER, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
            postRepository.save(post1);
            postRepository.save(post2);

            // 더미 댓글 생성
            Comment comment1 = new Comment(null, user1, post1, "댓글1", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
            Comment comment2 = new Comment(null, user2, post1, "댓글2", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
            Comment comment3 = new Comment(null, user1, post2, "댓글3", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
            commentRepository.save(comment1);
            commentRepository.save(comment2);
            commentRepository.save(comment3);
            System.out.println("======= Users =======");
            userRepository.findAll().forEach(System.out::println);

            System.out.println("======= Posts =======");
            postRepository.findAll().forEach(System.out::println);

            System.out.println("======= Comments =======");
            commentRepository.findAll().forEach(System.out::println);
        };
    }
}
