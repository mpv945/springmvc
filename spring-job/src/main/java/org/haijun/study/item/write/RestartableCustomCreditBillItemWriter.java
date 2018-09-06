/**
 * 
 */
package org.haijun.study.item.write;

import org.haijun.study.model.bo.CreditBill;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * 自定义ItemWriter，如果不需要支持重启，不要实现ItemStream接口
 * 2013-9-29下午12:18:47
 */
public class RestartableCustomCreditBillItemWriter implements ItemWriter<CreditBill>, ItemStream {
	private List<CreditBill> result = new ArrayList<CreditBill>();
	private int currentLocation = 0;
	private static final String CURRENT_LOCATION = "current.location";
	
	@Override
	public void write(List<? extends CreditBill> items) throws Exception {
		for(;currentLocation < items.size();){
			result.add(items.get(currentLocation++));
		}
	}

	@Override
	public void open(ExecutionContext executionContext)
			throws ItemStreamException {
		if(executionContext.containsKey(CURRENT_LOCATION)){
			currentLocation = new Long(executionContext.getLong(CURRENT_LOCATION)).intValue();
        }else{
        	currentLocation = 0;
        }
	}

	@Override
	public void update(ExecutionContext executionContext)
			throws ItemStreamException {
		executionContext.putLong(CURRENT_LOCATION, new Long(currentLocation).longValue());
	}

	@Override
	public void close() throws ItemStreamException {}

	public List<CreditBill> getResult() {
		return result;
	}
}
