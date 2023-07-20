package com.snowball.board.config;

import com.snowball.board.common.batch.UserPostCountUpdateTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final UserPostCountUpdateTasklet userPostCountUpdateTasklet;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                       UserPostCountUpdateTasklet userPostCountUpdateTasklet) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.userPostCountUpdateTasklet = userPostCountUpdateTasklet;
    }

    @Bean
    public Step userPostCountUpdateStep() {
        return stepBuilderFactory.get("userPostCountUpdateStep")
                .tasklet(userPostCountUpdateTasklet)
                .build();
    }

    @Bean
    public Job updateUserPostCountJob(Step userPostCountUpdateStep) {
        return jobBuilderFactory.get("updateUserPostCountJob")
                .incrementer(new RunIdIncrementer())
                .start(userPostCountUpdateStep)
                .build();
    }
}

