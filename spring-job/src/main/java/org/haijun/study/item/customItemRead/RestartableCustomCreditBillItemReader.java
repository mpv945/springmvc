/**
 * 
 */
package org.haijun.study.item.customItemRead;

import org.haijun.study.model.bo.CreditBill;
import org.springframework.batch.item.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持重启
 * 2013-9-7下午02:16:52
 */
public class RestartableCustomCreditBillItemReader implements ItemReader<CreditBill>,ItemStream{
	private List<CreditBill> list = new ArrayList<CreditBill>();
	private int currentLocation = 0;//存储的标志位置值
	private static final String CURRENT_LOCATION = "current.location";//存储的key
	
	public RestartableCustomCreditBillItemReader(List<CreditBill> list){
		this.list = list;
	}

	@Override
	public CreditBill read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		if (!list.isEmpty()) {
            return list.get(currentLocation++);
        }
		return null;
	}

	// 从执行上下文中获取当前读取的位置
	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		if(executionContext.containsKey(CURRENT_LOCATION)){
			currentLocation = new Long(executionContext.getLong(CURRENT_LOCATION)).intValue();
        }else{
        	currentLocation = 0;
        }
	}

	// 将当前已读过的数据位置存放在执行上下文中，通常update操作在chunk的事务提交后会执行一次
	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		executionContext.putLong(CURRENT_LOCATION, new Long(currentLocation).longValue());
	}

	/*通常在此次关闭不再需要的资源*/
	@Override
	public void close() throws ItemStreamException {}
}
