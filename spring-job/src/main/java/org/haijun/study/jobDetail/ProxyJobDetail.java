package org.haijun.study.jobDetail;

import org.haijun.study.utils.ApplicationContextUtil;
import org.quartz.*;

/**
 * jobDetail代理类，通过该类去调用实际的Job实现类
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution //不允许同一个任务同时多次执行，
public class ProxyJobDetail implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap =jobExecutionContext.getJobDetail().getJobDataMap();
        String targetObject = (String) jobDataMap.get("targetObject");
        System.out.println("####开始执行:{}任务###"+targetObject);
        Job job = (Job) ApplicationContextUtil.getBean(targetObject);
        job.execute(jobExecutionContext);
        System.out.println("####{},任务执行完成####"+targetObject);
    }
}
