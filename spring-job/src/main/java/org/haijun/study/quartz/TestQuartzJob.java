package org.haijun.study.quartz;

import org.quartz.*;
import org.quartz.Job;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

public class TestQuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("quartz开始调度");
        // 获取配置的jobDataMap参数
        JobDataMap jobDataMap =jobExecutionContext.getJobDetail().getJobDataMap();
        String jobRepositoryStr = (String) jobDataMap.get("jobRepository");
        String jobLauncherStr = (String) jobDataMap.get("jobLauncher");
        String batchJob = (String) jobDataMap.get("batchJob");

        // 生成batch Job的唯一参数
        UUID uuid = UUID.randomUUID();
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addString("jobParamUUID",uuid.toString());
        JobParameters params = builder.toJobParameters();

        //获取Spring中的上下文（配置文件applicationContextSchedulerContextKey 设定过）
        //获取JobExecutionContext中的service对象
        SchedulerContext schCtx = null;
        try {
            schCtx = jobExecutionContext.getScheduler().getContext();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        ApplicationContext appCtx = (ApplicationContext)schCtx.get("applicationContext");

        //在spring batch当中同样的参数与jobName组合成一个具体的job实例化，为防止重复执行可以在启动之前先判断是否已经执行过了
        org.springframework.batch.core.Job bJob = (org.springframework.batch.core.Job)appCtx.getBean(batchJob);
        String jobName = bJob.getName();
        JobRepository jobRepository = (JobRepository)appCtx.getBean(jobRepositoryStr);
        JobExecution jobExecution = jobRepository.getLastJobExecution(jobName, params);
        if (jobExecution != null) {
            String exitCode = jobExecution.getExitStatus().getExitCode();
            if (ExitStatus.COMPLETED.equals(exitCode)) {
                System.out.println("当前任务已经执行完成");
            } else if (ExitStatus.FAILED.equals(exitCode)) {
                //对于已经失败的任务，可以通过重启的方式实现，或根据实际的业务处理
                //重启操作需要接入spring batch admin 或自己编写对应的实现类
                System.out.println("当前任务已经执行完成,但结果为失败");
            }
        }

        System.out.println("任务开始启动，jobPamrams:"+params.toString());
        try {
            JobLauncher jobLauncher = (JobLauncher)appCtx.getBean(jobLauncherStr);
            jobLauncher.run(bJob, params);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
        System.out.println("quartz调度完成");
    }
}
