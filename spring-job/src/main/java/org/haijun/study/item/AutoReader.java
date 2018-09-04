/**
 * 
 */
package org.haijun.study.item;

import org.springframework.batch.item.*;

/**
 * 定义一个简单读。从0开始读数据，直到Integer最大值
 * 2013-3-19下午08:54:49
 */
public class AutoReader implements ItemReader<String> , ItemStream {
	private short count = 0;

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException{
		return ++count + "";
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {


	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {

	}

	@Override
	public void close() throws ItemStreamException {

	}
}
