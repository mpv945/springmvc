/**
 * 
 */
package org.haijun.study.item;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * 定义一个简单读。从0开始读数据，直到Integer最大值
 * 2013-3-19下午08:54:49
 */
public class AutoReader implements ItemReader<String> {
	private short count = 0;
	
	public String read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		return ++count + "";
	}

}
