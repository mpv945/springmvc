/**
 * 
 */
package org.haijun.study.flow.decider;

import org.haijun.study.flow.Constant;
import org.haijun.study.flow.CreditService;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * 自定义决策条件实现
 * 2013-10-7上午10:23:12
 */
public class FileExistsDecider implements JobExecutionDecider {
	private CreditService creditService;

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.job.flow.JobExecutionDecider#decide(org.springframework.batch.core.JobExecution, org.springframework.batch.core.StepExecution)
	 */
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution,
			StepExecution stepExecution) {
		String readFileName = stepExecution.getJobParameters().getString(Constant.READ_FILE_NAME);
		String workDirectory = stepExecution.getJobParameters().getString(Constant.WORK_DIRECTORY);
		if(creditService.exists(workDirectory+readFileName)) {
			// FlowExecutionStatus.FAILED 系统状态
			return new FlowExecutionStatus("FILE EXISTS"); //自定义状态 文件存在返回退出状态标识为FILE EXISTS
		} else {
			return new FlowExecutionStatus("NO FILE");
		}
	}

	public void setCreditService(CreditService creditService) {
		this.creditService = creditService;
	}

}
