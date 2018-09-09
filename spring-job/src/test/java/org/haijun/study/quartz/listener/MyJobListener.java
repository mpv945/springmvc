package org.haijun.study.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * 监听器 SchedulerListener
 */
public class MyJobListener  implements JobListener {
    @Override
    public String getName() {
        return "MyJobListerner";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("Job监听器：MyJobListener.jobToBeExecuted()");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("Job监听器：MyJobListener.jobExecutionVetoed()");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("Job监听器：MyJobListener.jobWasExecuted()");
    }
}
