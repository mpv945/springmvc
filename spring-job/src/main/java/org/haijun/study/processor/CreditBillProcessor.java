package org.haijun.study.processor;

import org.haijun.study.model.bo.CreditBill;
import org.springframework.batch.item.ItemProcessor;

/**
 * ItemProcessor 接口有两个泛型参数，一个为i：输入流；另一个o为输出流
 */
public class CreditBillProcessor implements ItemProcessor<CreditBill, CreditBill> {
    @Override
    public CreditBill process(CreditBill creditBill) throws Exception {
        System.out.println(creditBill.toString());
        return creditBill;
    }
}
