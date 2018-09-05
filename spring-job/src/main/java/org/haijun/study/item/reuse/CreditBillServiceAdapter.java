/**
 * 
 */
package org.haijun.study.item.reuse;

import org.haijun.study.model.bo.CreditBill;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-7下午01:40:07
 */
public class CreditBillServiceAdapter implements InitializingBean{

	private ExistService existService;

	List<CreditBill> creditBillList;

	// 在spring 组装对象之后执行
	/*初始化bean的时候执行，可以针对某个具体的bean进行配置。afterPropertiesSet 必须实现 InitializingBean接口。实现
	InitializingBean接口必须实现afterPropertiesSet方法。*/
	@Override
	public void afterPropertiesSet() throws Exception {
		this.creditBillList = existService.queryAllCreditBill();
	}

	// 没次读取返回第一条信息，直到读取为null
	public CreditBill nextCreditBill() {
		if (creditBillList.size() > 0) {
			return creditBillList.remove(0);
		} else {
			return null;
		}
	}

	public ExistService getExistService() {
		return existService;
	}

	public void setExistService(ExistService existService) {
		this.existService = existService;
	}
}
