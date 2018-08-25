/**
 * 
 */
package org.haijun.study.job.ch02;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-2-28下午08:34:48
 */
public class JobLaunch {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//simpleRunJob();// 简单运行

		String jobConfgPath = "job/ch02/job.xml";
		String jobName = "billJob";
		JobParametersBuilder parametersBuilder = new JobParametersBuilder().
				addString("jobInstanceKey",LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));// 设置调用Job时候的参数名jobInstanceKey，以时间来区分唯一
		parameteerRunJob(jobConfgPath, jobName, parametersBuilder);// 带参数运行，结果日志会输出jobParameters=[{jobInstanceKey=2018-08-26T01:20:51.98}]
	}

	public static void parameteerRunJob(String jobConfgPath, String jobName, JobParametersBuilder parametersBuilder){
		ApplicationContext context = new ClassPathXmlApplicationContext(jobConfgPath);
		JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(jobName);
		try {
			JobExecution result = launcher.run(job, parametersBuilder.toJobParameters());// 通过JobParametersBuilder指定参数运行Job
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void simpleRunJob(){
		ApplicationContext context = new ClassPathXmlApplicationContext("job/ch02/job.xml");
		JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("billJob");
		try {
			JobExecution result = launcher.run(job, new JobParameters());// 通过JobLauncher的run方法执行job
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
