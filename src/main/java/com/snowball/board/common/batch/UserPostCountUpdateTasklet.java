package com.snowball.board.common.batch;

import com.snowball.board.common.util.UserRole;
import com.snowball.board.domain.user.model.User;
import com.snowball.board.domain.user.repository.UserRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserPostCountUpdateTasklet implements Tasklet {

    private final Long POST_COUNT_THRESHOLD = 10L;

    private final UserRepository userRepository;

    public UserPostCountUpdateTasklet(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        List<User> usersWithPostCount = userRepository.getUsersWithPostCountGreaterThan(POST_COUNT_THRESHOLD);

        for (User user : usersWithPostCount) {
            System.out.println(user.toString());
            if (user.getUserRole() == UserRole.BEGINNER) {
                user.updateUserRole(UserRole.EXPERT);
            }

            userRepository.save(user);
        }

        return RepeatStatus.FINISHED;
    }
}
