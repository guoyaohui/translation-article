
package com.category.batch.reports.config;

import com.category.batch.reports.tasklet.ReportTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportsConfig {

    public final static String jobName = "ReportsJob1";
    @Autowired
    private JobRegistry jobRegistry;
    @Autowired
    private Tasklet taskletstep;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public JobBuilderFactory getJobBuilderFactory() {
        return jobBuilderFactory;
    }

    public void setJobBuilderFactory(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public ReportTasklet reportTasklet() {
        return new ReportTasklet();
    }

    @Bean
    public Job job() throws DuplicateJobException {
        Job job = getJobBuilderFactory().get(jobName).start(getStep()).build();
        return job;
    }

    @Bean
    public Step getStep() {
        return stepBuilderFactory.get("step").tasklet(reportTasklet()).build();
    }
}