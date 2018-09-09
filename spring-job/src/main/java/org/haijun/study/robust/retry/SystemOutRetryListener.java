/**
 * 
 */
package org.haijun.study.robust.retry;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

/**
 * 重试监听器
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-21下午10:10:26
 */
public class SystemOutRetryListener implements RetryListener {

	//在进入retry之前执行该操作，可以在该操作中准备要重试的资源，如果返回false，将会终止本次重试操作，并且抱出异常TerminatedRetryException
	@Override
	public <T> boolean open(RetryContext retryContext, RetryCallback<T> retryCallback) {
		//return false;
		System.out.println("SystemOutRetryListener.open()");
		return true;
	}
	// 重试结束之前执行该操作，可以在该操作关闭在open打开的资源
	@Override
	public <T> void close(RetryContext retryContext, RetryCallback<T> retryCallback, Throwable throwable) {
		System.out.println("SystemOutRetryListener.close()");
	}
	// 重试发生错误的时候触发该操作
	@Override
	public <T> void onError(RetryContext retryContext, RetryCallback<T> retryCallback, Throwable throwable) {
		System.out.println("SystemOutRetryListener.onError()");
	}
}
