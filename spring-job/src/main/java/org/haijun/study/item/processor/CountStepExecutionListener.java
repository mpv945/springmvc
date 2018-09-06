/**
 * 
 */
package org.haijun.study.item.processor;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

/**
 * 数据过滤统计
 * 2013-9-30下午03:20:11
 */
public class CountStepExecutionListener extends StepExecutionListenerSupport {
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		int skipCount = stepExecution.getSkipCount();//跳过数量统计
		int filterCount = stepExecution.getFilterCount();//过滤的个数；里面还能获取更多统计数
		System.out.println("Skip count=" + skipCount);
		return stepExecution.getExitStatus();
	}
}
