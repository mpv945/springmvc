/**
 * 
 */
package org.haijun.study.item.processor;

import org.haijun.study.model.bo.CreditBill;
import org.springframework.batch.item.ItemProcessor;

/**
 * 处理过滤，对于大于500就过滤掉
 * 2013-9-30下午02:42:12
 */
public class FilterItemProcessor implements ItemProcessor<CreditBill, CreditBill> {

	@Override
	public CreditBill process(CreditBill item) throws Exception {
		if(item.getAmount()> 500){
			return null;//处理器返回null，说明该数据被过滤掉
		}else{
			return item;
		}
	}
}
