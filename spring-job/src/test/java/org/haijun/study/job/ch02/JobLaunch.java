/**
 * 
 */
package org.haijun.study.job.ch02;

import org.springframework.batch.core.*;
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

		//String jobConfgPath = "job/ch02/job.xml";// 内存数据
		String jobConfgPath = "job/job-context-db.xml";//包含job的数据库创建访问
		String jobName = "billJob";
		// 构建参数
		JobParametersBuilder parametersBuilder = new JobParametersBuilder().
				addString("jobInstanceKey",LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));// 设置调用Job时候的参数名jobInstanceKey，以时间来区分唯一
		// 此次可以模拟运行多次请求，主要验证Job 不同参数会创建不同的Job 实例(Instance)，任务失败，通过启动相同的参数再次执行该Job，并且获取实例还是失败的那个实例
		/* 执行步骤和说明
		1 . 以参数为 new JobParametersBuilder().addString("20180826");
				执行一次任务，执行完：JOB_INSTANCE 会新增一条数据（根据参数序列生成JOB_KEY和JOB_NAME 来唯一一个实例）；
												   每次运行JOB ，都会创建实例。先会查根据JOB_NAME和JOB_KEY查询JOB_INSTANCE，没有返回null，让框架创建，有，就根据JOB_INSTANCE_ID去JOB_EXECUTION表查询STATUS状态，只有BatchStatus.FAILED失败才能获取该实例重新执行
									  JOB_INSTANCE 表说明：作业执行器的状态记录，对JOB执行过程跟踪，根据JOB_INSTANCE_ID 实例ID跟踪
		2. 以参数为 new JobParametersBuilder().addString("20180829"); 执行一次出错的（把csv文件的值改成类型错误的数值，这时JOB_INSTANCE 会新增一条status为错误的任务
		3. 把csv数据改成正确的，这时新增20180829（JOB_INSTANCE 还是之前失败那条） 的JOB_INSTANCE状态变成成功（重启任务，实例创建测试完毕）

		*/
		// BatchStatus.FAILED
		parameteerRunJob(jobConfgPath, jobName, parametersBuilder);// 带参数运行，结果日志会输出jobParameters=[{jobInstanceKey=2018-08-26T01:20:51.98}]，不带 new JobParameters());//jobParameters=[{}]
	}

	/**
	 * 简单参数运行job
	 * @param jobConfgPath
	 * @param jobName
	 * @param parametersBuilder
	 */
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
