/**
 * 
 */
package org.haijun.study.handler;

import org.springframework.batch.item.file.LineCallbackHandler;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-4-3上午09:41:08
 */
public class DefaultLineCallbackHandler implements LineCallbackHandler {


	@Override
	public void handleLine(String s) {
		System.out.println("跳过行的内容为:" + s);
	}
}
