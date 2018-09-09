/**
 * 
 */
package org.haijun.study.robust.retry.template;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryListener;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-22下午08:44:02
 */
public class CreditBillTasklet implements Tasklet {

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		RetryCallback<String> retryCallback = new DefaultRetryCallback();//重试回调定义，当发生指定（Exception）的错误时候会被执行回调
		RetryListener[] listeners = new RetryListener[]{new CountRetryListener()};//定义重试拦截器
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();// 定义重试策略
		retryPolicy.setMaxAttempts(3);//重试策略最大次数为3
		RetryTemplate template = new RetryTemplate();//使用重试模板执行回调操作
		template.setRetryPolicy(retryPolicy);//重试策略
		template.setListeners(listeners);//重试监听器
		template.execute(retryCallback);//执行重试模板
		return RepeatStatus.FINISHED;
	}

}
