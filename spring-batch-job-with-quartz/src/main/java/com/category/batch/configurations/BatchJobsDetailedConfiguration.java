package com.category.batch.configurations;

import com.category.batch.job.JobLauncherDetails;
import com.category.batch.reports.config.ReportsConfig;
import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class BatchJobsDetailedConfiguration {

    @Autowired
    private JobLauncher jobLauncher;

    @Bean(name = "reportsDetailContext")
    public ApplicationContextFactory getApplicationContext() {
        return new GenericApplicationContextFactory(ReportsConfig.class);
    }

    @Bean(name = "reportsDetailJob")
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(JobLauncherDetails.class);
        jobDetailFactoryBean.setDurability(true);
        Map<String, Object> map = new HashMap<>();
        map.put("jobLauncher", jobLauncher);
        map.put("jobName", ReportsConfig.jobName);
        jobDetailFactoryBean.setJobDataAsMap(map);
        return jobDetailFactoryBean;
    }

    @Bean(name = "reportsCronJob")
    public CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean().getObject());
        cronTriggerFactoryBean.setCronExpression("0 0/1 * 1/1 * ? *");
        return cronTriggerFactoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobRegistry jobRegistry) throws NoSuchJobException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(cronTriggerFactoryBean().getObject());
        schedulerFactoryBean.setAutoStartup(true);
        Map<String, Object> map = new HashMap<>();
        map.put("jobLauncher", jobLauncher);
        map.put("jobLocator", jobRegistry);
        schedulerFactoryBean.setSchedulerContextAsMap(map);
        return schedulerFactoryBean;
    }
}
