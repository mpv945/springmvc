/**
 * 
 */
package org.haijun.study.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * JobExecutionListener 框架默认实现有两个（拦截器发生异常，会导致Job执行状态为FAILED
 * 			1 .CompositeJobExecutionListener  拦截器组合模式，支持一组拦截器的调用
 * 			2. JobExecutionListenerSupport    JobExecutionListener 的空实现，可以直接继承，只要覆盖关系的方法
 * 2013-3-9下午08:46:28
 */
public class SystemOutJobExecutionListener implements JobExecutionListener {

	/* Job 执行之前调用该方法
	 * @see org.springframework.batch.core.JobExecutionListener#beforeJob(org.springframework.batch.core.JobExecution)
	 */
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("接口实现：JobExecution creat time:" + jobExecution.getCreateTime());
//		throw new RuntimeException("listener make error!");
	}

	/* Job 执行之后调用该方法
	 * @see org.springframework.batch.core.JobExecutionListener#afterJob(org.springframework.batch.core.JobExecution)
	 */
	public void afterJob(JobExecution jobExecution) {
		System.out.println("接口实现Job execute state:" + jobExecution.getStatus().toString());
	}

}
