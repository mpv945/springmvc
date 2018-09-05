/**
 * 
 */
package org.haijun.study.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-10上午07:38:39
 */
public class SystemOut {
	@BeforeJob
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("注解监听: JobExecution creat time:" + jobExecution.getCreateTime());
//		throw new RuntimeException("listener make error!");
	}

	@AfterJob
	public void afterJob(JobExecution jobExecution) {
		System.out.println("注解监听: Job execute state:" + jobExecution.getStatus().toString());
	}
}