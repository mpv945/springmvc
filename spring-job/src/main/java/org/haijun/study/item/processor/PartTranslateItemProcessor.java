/**
 * 
 */
package org.haijun.study.item.processor;

import org.haijun.study.model.bo.CreditBill;
import org.springframework.batch.item.ItemProcessor;


/**
 * 
 * 自定义转换器
 * 2013-9-30上午10:34:34
 */
public class PartTranslateItemProcessor implements ItemProcessor<CreditBill, CreditBill> {

	public CreditBill process(CreditBill bill) throws Exception {
		bill.setAddress(bill.getAddress() + "," + bill.getName());
		return bill;
	}
}
