/**
 * 
 */
package org.haijun.study.robust.skip;

import java.util.Random;

import org.springframework.batch.item.ItemProcessor;

/**
 * 如果能被2整除的就抱出异常
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-20下午10:06:48
 */
public class RadomExceptionItemProcessor implements ItemProcessor<String, String> {
	Random ra = new Random();
	
	public String process(String item) throws Exception {
		int i = ra.nextInt(10);
		System.out.println("Process " + item + "; Random i=" + i);
		if(i%2 == 0){
			throw new RuntimeException("make error!");
		}else{
			return item;
		}
	}
}
