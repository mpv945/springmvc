/**
 * 
 */
package org.haijun.study.handler;

import org.haijun.study.model.bo.CreditBill;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-4-6上午07:13:55
 */
public class DebitBillFieldSetMapper implements FieldSetMapper<CreditBill> {

	public CreditBill mapFieldSet(FieldSet fieldSet) throws BindException {
		CreditBill result = new CreditBill();
		result.setAccountID(fieldSet.readString("accountID"));
		result.setName(fieldSet.readString("name"));
		result.setAmount(fieldSet.readDouble("amount"));
		result.setDate(fieldSet.readString("date"));
		return result;
	}
}
