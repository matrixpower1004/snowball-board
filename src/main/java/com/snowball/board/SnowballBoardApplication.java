package com.snowball.board;

import com.snowball.board.domain.board.repository.CommentRepository; // 추가: CommentRepository import
import com.snowball.board.domain.board.repository.PostRepository;
import com.snowball.board.domain.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SnowballBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnowballBoardApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDummyData(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) { // 추가: CommentRepository 매개변수로 추가
        return args -> {

        };
    }
}
