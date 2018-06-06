package org.haijun.study.config;

import org.haijun.study.scheduled.ScheduledTaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
//@ComponentScan("org.light4j.sping4.senior.taskscheduler") //换成@Bean
@EnableScheduling
public class TaskSchedulerConfig {

    @Bean
    public ScheduledTaskService scheduledTaskService(){
        return new ScheduledTaskService();
    }
}
