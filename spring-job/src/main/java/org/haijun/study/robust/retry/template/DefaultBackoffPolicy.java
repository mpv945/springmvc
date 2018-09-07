/**
 * 
 */
package org.haijun.study.robust.retry.template;

import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.BackOffPolicy;

/**
 *  业务补偿操作，每次重试都会触发该接口的backOff操作
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-21下午10:59:56
 */
public class DefaultBackoffPolicy implements BackOffPolicy {

	// start 方法在重试过程中仅执行一次
	public BackOffContext start(RetryContext context) {
		BackOffContextImpl backOffContext = new BackOffContextImpl(context);
		return backOffContext;
	}

	// backOff操作在每次重试发生后都会触发该补偿操作。
	public void backOff(BackOffContext backOffContext)
			throws BackOffInterruptedException {
		//Assert.assertNotNull(((BackOffContextImpl)backOffContext).getRetryContext().getAttribute("count"));
		CountHelper.decrement();// 计算器减1
	}

}
