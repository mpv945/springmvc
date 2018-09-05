/**
 * 
 */
package org.haijun.study.item.customItemRead;

import org.haijun.study.model.bo.CreditBill;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.List;

/**
 * 不可重启的ItemReader实现
 * 2013-9-7下午02:12:56
 */
public class CustomCreditBillItemReader implements ItemReader<CreditBill> {

	private List<CreditBill> list = new ArrayList<CreditBill>();
	
	public CustomCreditBillItemReader(List<CreditBill> list){
		this.list = list;
	}

	@Override
	public CreditBill read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (!list.isEmpty()) {
            return list.remove(0);
        }
		return null;// 返回null标识读完了
	}
}
