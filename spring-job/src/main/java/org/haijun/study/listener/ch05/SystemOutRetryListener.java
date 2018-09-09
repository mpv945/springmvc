/**
 * 
 */
package org.haijun.study.listener.ch05;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-24下午08:26:10
 */
public class SystemOutRetryListener implements RetryListener {
	// 重试开始，进入retry之前执行该操作，可以在该操作中准备需要的资源。如果操作返回false，将会终止本操作，并抱出TerminatedRetry异常
	@Override
	public <T> boolean open(RetryContext retryContext, RetryCallback<T> retryCallback) {
		return false;
	}

	// 重试结束 retry结束之前执行该操作，可以在这关闭open中打开的资源
	@Override
	public <T> void close(RetryContext retryContext, RetryCallback<T> retryCallback, Throwable throwable) {

	}

	// 重试处理发生异常
	@Override
	public <T> void onError(RetryContext retryContext, RetryCallback<T> retryCallback, Throwable throwable) {

	}
}
