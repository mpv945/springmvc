/**
 * 
 */
package org.haijun.study.handler;

import org.haijun.study.model.bo.CreditBill;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.JsonLineMapper;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;

import java.util.Map;

/**
 * LineMapper 接口系统默认实现
 * 		DefaultLineMapper 默认行转换类，引用LineTokenizer和FieldSetMapper完成数据转换，LineTokenizer赋值将一条记录转成FieldSet(key-val数据格式)，FieldSetMapper复制转java对象
 * 		JsonLineMapper 负责将文件中JSON格式文本数据转换为领域对象，转换后的领域对象格式为Map<String,Object>
 * 		PassThroughLineMapper  最简单的数据转换实现，将一条记录直接返回，可以认为返回的就是String 类型字符串
 * 		PatternMatchingCompositeLineMapper 复杂数据类型转换，（组合多个DefaultLineMapper）可以为不同的记录定义不同的LineTokenizer和FieldSetMapper，可以根据条件找到匹配的DefaultLineMapper
 * 自定义JSON转java的映射操作
 * 2013-4-3上午10:57:50
 */
public class WrappedJsonLineMapper implements LineMapper<CreditBill> {

	private JsonLineMapper delegate;

	public JsonLineMapper getDelegate() {
		return delegate;
	}

	public void setDelegate(JsonLineMapper delegate) {
		this.delegate = delegate;
	}


	@Override
	public CreditBill mapLine(String s, int i) throws Exception {

		CreditBill result = new CreditBill();
		// 通过JsonLineMapper获取Map<String, Object>类型数据
		Map<String, Object> creditBillMap = delegate.mapLine(s, i);
		// 将Map<String, Object>数据转换成JavaBean
		result.setAccountID((String)creditBillMap.get("accountID"));
		result.setName((String)creditBillMap.get("name"));
		result.setAmount((Double)creditBillMap.get("amount"));
		result.setDate((String)creditBillMap.get("date"));
		result.setAddress((String)creditBillMap.get("address"));
		return result;
	}
}
