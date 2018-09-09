/**
 * 
 */
package org.haijun.study.robust.retry.template;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

/**
 * 拦截器每次重试
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-22下午02:06:57
 */
public class CountRetryListener implements RetryListener {


	/**
	 * 在进入retry之前执行该操作，可以在该操作中准备要重试的资源，如果返回false，将会终止本次重试操作，并且抱出异常TerminatedRetryException
	 * @param retryContext
	 * @param retryCallback
	 * @return
	 */
	@Override
	public <T> boolean open(RetryContext retryContext, RetryCallback<T> retryCallback) {
		//return false;
		return true;
	}

	@Override
	public <T> void close(RetryContext retryContext, RetryCallback<T> retryCallback, Throwable throwable) {

	}

	@Override
	public <T> void onError(RetryContext retryContext, RetryCallback<T> retryCallback, Throwable throwable) {
		CountHelper.increment();//在发生重试异常时候每次加1
		System.out.println("CountRetryListener.onError().");
	}
}
