/**
 * 
 */
package org.haijun.study.listener.ch05;

import org.springframework.batch.core.SkipListener;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2012-9-1上午08:18:40
 */
public class SystemOutSkipListener implements SkipListener<String, String> {

	/**
	 * 读错误导致跳过时
	 * @param t
	 */
	public void onSkipInRead(Throwable t) {
		System.out.println("SkipListener.onSkipInRead()");		
	}

	/**
	 * 写操作导致跳过时
	 * @param item
	 * @param t
	 */
	public void onSkipInWrite(String item, Throwable t) {
		System.out.println("SkipListener.onSkipInWrite()");
	}

	// 处理错误导致跳过时
	public void onSkipInProcess(String item, Throwable t) {
		System.out.println("SkipListener.onSkipInProcess()");		
	}

}
