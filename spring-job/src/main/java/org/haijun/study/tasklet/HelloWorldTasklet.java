/**
 * 
 */
package org.haijun.study.tasklet;

import java.util.Map;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Step的具体执行逻辑操作
 * 2013-3-19下午03:56:36
 */
public class HelloWorldTasklet implements Tasklet {

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		String jobName = chunkContext.getStepContext().getJobName();
		System.out.println("执行的 job名 :" + jobName +".");
		JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
		System.out.println("执行参数 :" + jobParameterToString(jobParameters));
		return RepeatStatus.FINISHED;
	}
	
	/**
	 * 转换为String类型格式.<br>
	 * @param jobParameters
	 * @return
	 */
	private String jobParameterToString(JobParameters jobParameters){
		StringBuffer sb = new StringBuffer();
		for(Map.Entry<String, JobParameter> param : jobParameters.getParameters().entrySet()) {
			sb.append(String.format(
				"%s = %s (%s);",
				param.getKey(),param.getValue().getValue(),param.getValue().getType()
			));
		}
		return sb.toString();
	}

}