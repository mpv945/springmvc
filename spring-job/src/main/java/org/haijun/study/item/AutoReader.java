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

	// 根据参数executionContext打开需要读取资源的stream，可以根据持久在执行上下文ExecutionContext中的数据重新定位需要读取记录的位置
	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {


	}

	// 把需要持久化的数据存在执行上下文的executionContext中
	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
	}

	//关闭读取的资源
	@Override
	public void close() throws ItemStreamException {

	}
}
