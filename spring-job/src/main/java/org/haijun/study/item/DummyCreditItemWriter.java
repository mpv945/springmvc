/**
 * 
 */
package org.haijun.study.item;

import org.haijun.study.model.bo.CreditBill;
import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-8-16下午03:18:17
 */
public class DummyCreditItemWriter implements ItemWriter<CreditBill> {
	
	public List<CreditBill> creditBills = new ArrayList<CreditBill>();

	public void write(List<? extends CreditBill> items) throws Exception {
		creditBills.addAll(items);
	}

	public List<CreditBill> getCredits() {
		return creditBills;
	}
}
