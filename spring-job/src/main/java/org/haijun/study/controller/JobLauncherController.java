/**
 * 
 */
package org.haijun.study.controller;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.haijun.study.item.StopStepListener;
import org.haijun.study.utils.ApplicationContextUtil;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-20下午09:18:12
 */
@Controller
public class JobLauncherController {
	
	private static final String JOB_NAME = "jobName";
	private JobLauncher jobLauncher;
	private JobRegistry jobRegistry;
	private JobOperator jobOperator;


	
	public JobLauncherController(JobLauncher jobLauncher, JobRegistry jobRegistry,
								 JobOperator jobOperator) {
		this.jobLauncher = jobLauncher;
		this.jobRegistry = jobRegistry;
		this.jobOperator = jobOperator;
	}

	/**
	 * 测试地址http://localhost:8080/executeJob?jobName=chunkJob&date=20180902
	 * web 执行Job
	 * @param jobName 要执行的Job名字,
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/executeJob",method=RequestMethod.GET)
	public void launch(@RequestParam(value = "jobName") String jobName , HttpServletRequest request)
			throws Exception {
		JobParameters jobParameters = bulidParameters(request);
		// 执行具体的Job' jobRegistry中存放spring 配置文件中定义的所有的Job信息(如果是停止过的任务，可以再次接着上次重新启动
		jobLauncher.run( jobRegistry.getJob(jobName),jobParameters);
		System.out.println("同步处理完成");
	}

	/**
	 * http://localhost:8080/stopJob?jobName=chunkJob
	 * 停止job任务
	 * @param jobName
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/stopJob",method=RequestMethod.GET)
	public void stop(@RequestParam(value = "jobName") String jobName , HttpServletRequest request) throws Exception{
		Set<Long> runningExecutions = jobOperator.getRunningExecutions(jobName);
		Iterator<Long> iterator = runningExecutions.iterator();
		while(iterator.hasNext()){
			boolean sendMessage = jobOperator.stop(iterator.next());
			System.out.println("停止作业{sendMessage:" + sendMessage+"}");
		}
	}

	// 通过监听器停止任务
	@RequestMapping(value="/listenerStopJob",method=RequestMethod.GET)
	public void listenerStop() throws Exception {
		StopStepListener<?> stopListener = (StopStepListener<?>) ApplicationContextUtil.getBean("stopListener");
		stopListener.setStop(Boolean.TRUE);
	}

	// 将请求参数转换成作业参数
	private JobParameters bulidParameters(HttpServletRequest request) {
		JobParametersBuilder builder = new JobParametersBuilder();
		@SuppressWarnings("unchecked")
		Enumeration<String> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if(!JOB_NAME.equals(paramName)) {
				builder.addString(paramName,request.getParameter(paramName));
			}
		}
		return builder.toJobParameters();
	}
}
