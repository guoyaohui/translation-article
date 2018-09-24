
package com.category.batch.reports.tasklet;

import java.util.logging.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportTasklet implements Tasklet {

    private static final Logger logger = Logger.getLogger(ReportTasklet.class.getName());

    @Override
    public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) {
        try {
            logger.info("Report's Job is running. Add your business logic here.........");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RepeatStatus.FINISHED;
    }
}