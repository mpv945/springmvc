/**
 * 
 */
package org.haijun.study.flow.tasklet;

import org.haijun.study.flow.Constant;
import org.haijun.study.flow.CreditService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * 验证解压缩的文件是否存在，是否可读等
 * 2013-10-7上午10:20:31
 */
public class VerifyTasklet implements Tasklet {
	private String outputDirectory;
	private String readFileName;
	private CreditService creditService;
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		String status = creditService.verify(outputDirectory, readFileName);
		if (null != status) {
			// ExecutionContext 是框架提供的持久化与控制的key/value数据（可以通过key/value重启JOB），
			// ExecutionContext 分两类，一类 Job Execution的上下文(JOB_EXECUTION_CONTENXT(作业执行上下文表,存放上下文信息，)
			//                          另一类  Step Execution 上下文 (STEP_EXECUTION_CONTEXT(作业步执行器上下文,存放作业步执行的上下文信息)
			// 同一个Job，Step Execution 公用Job Execution的上下文，同一个job。不同step共享数据数据使用ExecutionContext
			chunkContext.getStepContext().getStepExecution()// 可以向上范围访问
					.getExecutionContext().put(Constant.VERITY_STATUS, status);//设定状态到ExecutionContext 上下文
		}
		return RepeatStatus.FINISHED;
	}

	public void setCreditService(CreditService creditService) {
		this.creditService = creditService;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public void setReadFileName(String readFileName) {
		this.readFileName = readFileName;
	}

}
