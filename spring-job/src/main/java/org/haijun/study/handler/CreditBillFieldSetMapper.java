/**
 * 
 */
package org.haijun.study.handler;

import org.haijun.study.model.bo.CreditBill;
import org.springframework.batch.item.file.mapping.ArrayFieldSetMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PassThroughFieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * FieldSetMapper 接口实现 FieldSet 转java对象，默认的实现：
 * 		ArrayFieldSetMapper 将FieldSet转换为String[]
 * 		BeanWrapperFieldSetMapper 将FieldSet对象根据名字映射到给定的Bean中，需要保证FieldSet字段名和Bean属性名称一致
 * 		PassThroughFieldSetMapper 直接返回FieldSet对象
 * 2013-4-3下午03:13:55
 */
public class CreditBillFieldSetMapper implements FieldSetMapper<CreditBill> {

	// 自定义实现该方法就好
	@Override
	public CreditBill mapFieldSet(FieldSet fieldSet) throws BindException {

		CreditBill result = new CreditBill();
		result.setAccountID(fieldSet.readString("accountID"));
		result.setName(fieldSet.readString("name"));
		result.setAmount(fieldSet.readDouble("amount"));
		result.setDate(fieldSet.readString("date"));
		result.setAddress(fieldSet.readString("address"));
		return result;
	}
}
