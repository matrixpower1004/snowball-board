package com.snowball.board.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class BatchSchedulerConfig {

    private final JobExplorer jobExplorer;
    private final JobLauncher jobLauncher;
    private final Job updateUserPostCountJob;

    public BatchSchedulerConfig(JobExplorer jobExplorer, JobLauncher jobLauncher, Job updateUserPostCountJob) {
        this.jobExplorer = jobExplorer;
        this.jobLauncher = jobLauncher;
        this.updateUserPostCountJob = updateUserPostCountJob;
    }

    @Scheduled(fixedRate = 60000) // Run every 1 minute
    public void runBatchJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters = new JobParametersBuilder(jobExplorer)
                .getNextJobParameters(updateUserPostCountJob)
                .toJobParameters();
        jobLauncher.run(updateUserPostCountJob, jobParameters);
    }
}

