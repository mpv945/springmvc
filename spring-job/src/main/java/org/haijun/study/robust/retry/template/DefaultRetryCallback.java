/**
 * 
 */
package org.haijun.study.robust.retry.template;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;

/**
 * 重试回调接口。实现需要重试的逻辑
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-21下午11:00:27
 */
public class DefaultRetryCallback implements RetryCallback<String> {
	private long sleepTime = 0L;
	
	public DefaultRetryCallback(){}
	public DefaultRetryCallback(long sleepTime){
		this.sleepTime = sleepTime;
	}

	// 重试发生时会多次调用该方法，会根据重试策略重复调用doWithRetry。直到不再满足重试策略
	@Override
	public String doWithRetry(RetryContext retryContext) throws Exception {
		Integer count = (Integer)retryContext.getAttribute("count");
		if(count == null){
			count = new Integer(0);
		}
		count++;
		retryContext.setAttribute("count", count);//设置重试次数
		Thread.sleep(sleepTime);
		throw new RuntimeException("Mock make exception on business logic.");
	}
}
