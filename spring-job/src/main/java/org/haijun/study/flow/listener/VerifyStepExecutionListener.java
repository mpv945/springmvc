/**
 * 
 */
package org.haijun.study.flow.listener;

import org.haijun.study.flow.Constant;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-7下午03:33:22
 */
public class VerifyStepExecutionListener implements StepExecutionListener {

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.StepExecutionListener#beforeStep(org.springframework.batch.core.StepExecution)
	 */
	@Override
	public void beforeStep(StepExecution stepExecution) { }

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.StepExecutionListener#afterStep(org.springframework.batch.core.StepExecution)
	 */
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {

		// 验证step执行完，获取ExecutionContext 设置的状态
		String status = stepExecution.getExecutionContext().getString(Constant.VERITY_STATUS);
		if(null != status){// 根据获取状态返回
			return new ExitStatus(status);
		}
		/*否则返回原本step的退出状态*/
		return stepExecution.getExitStatus();
	}

}
