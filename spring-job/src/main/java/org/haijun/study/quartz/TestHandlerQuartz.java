package org.haijun.study.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.UUID;

public class TestHandlerQuartz implements org.quartz.Job {

    /**
     * job 启动器可以根据实际情况注入同步或异步启动器
     */
    private JobLauncher jobLauncher;

    /**
     * 用于查询job实例是否已经存在
     */
    private JobRepository jobRepository;

    /**
     * 需要启动的job
     */
    private Job job;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        this.process();
    }

    public void process(){
        System.out.println("quartz开始调度");
        UUID uuid = UUID.randomUUID();
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addString("jobParamUUID",uuid.toString());
        JobParameters params = builder.toJobParameters();
        //在spring batch当中同样的参数与jobName组合成一个具体的job实例化，为防止重复执行可以在启动之前先判断是否已经执行过了
        String jobName = this.job.getName();
        JobExecution jobExecution = this.jobRepository.getLastJobExecution(jobName, params);
        if (jobExecution != null) {
            String exitCode = jobExecution.getExitStatus().getExitCode();

            if ("COMPLETED".equals(exitCode)) {
                System.out.println("当前任务已经执行完成");
            } else if ("FAILED".equals(exitCode)) {
                //对于已经失败的任务，可以通过重启的方式实现，或根据实际的业务处理
                //重启操作需要接入spring batch admin 或自己编写对应的实现类
                System.out.println("当前任务已经执行完成,但结果为失败");
            }
        }
        //启动job，如果有以下情况发生就会抛出相应的异常
        /*
         * 前提：同样的参数（不分先后方式）
         * 1.已经在执行
         * 2.以前执行过，但失败了，本次启动会默认重启以前的任务。
         * 3.任务已经执行完成
         * 4.参数格式不正确（根据job当中定义的参数校验规则来判断）
         */
        System.out.println("任务开始启动，jobPamrams:"+params.toString());
        try {
            this.jobLauncher.run(job, params);
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

    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }

    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public JobRepository getJobRepository() {
        return jobRepository;
    }

    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
