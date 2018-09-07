/**
 * 
 */
package org.haijun.study.robust.retry.template;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;

/**
 * RecoveryCallback 接口：重试执行完毕后，会触发恢复回调操作，通常在有状态的重试中
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-21下午11:00:17
 * @param <T>
 */
public class DefaultRecoveryCallback<T> implements RecoveryCallback<T> {

	/**
	 * 该操作在整个重试操作完成后被触发
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public T recover(RetryContext context) throws Exception {
		//Assert.assertNotNull(context.getAttribute("count"));
		CountHelper.decrement();
		return null;
	}

}
