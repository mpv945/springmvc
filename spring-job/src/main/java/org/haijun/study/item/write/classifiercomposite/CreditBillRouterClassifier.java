/**
 * 
 */
package org.haijun.study.item.write.classifiercomposite;


import org.haijun.study.model.bo.CreditBill;
import org.springframework.classify.annotation.Classifier;
/**
 * 自定义分类
 * 2013-9-23上午08:25:06
 */
public class CreditBillRouterClassifier{
	@Classifier
	public String classify(CreditBill classifiable) {
		if(classifiable.getAmount() > 500){ // 返回不同分类关键字来确定路由到哪个写入处理
			return "large";
		}else{
			return "small";
		}
	}
}
