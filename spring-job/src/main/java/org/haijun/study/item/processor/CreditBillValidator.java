/**
 * 
 */
package org.haijun.study.item.processor;

import org.haijun.study.model.bo.CreditBill;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

/**
 * 自定义数据验证处理器
 * 2013-9-30下午04:36:12
 */
public class CreditBillValidator implements Validator<CreditBill> {

	@Override
	public void validate(CreditBill creditBill) throws ValidationException {
		if(Double.compare(0, creditBill.getAmount()) >0) {//消费金额小于0抛出异常
			throw new ValidationException("Credit bill cannot be negative!");
		}	
	}
}
