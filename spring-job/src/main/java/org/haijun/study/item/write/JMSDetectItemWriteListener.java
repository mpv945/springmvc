/**
 * 
 */
package org.haijun.study.item.write;

import org.haijun.study.model.bo.CreditBill;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.jms.core.JmsTemplate;

/**
 * 该监听器在step执行完验证消息是否全部发生成功
 * 2013-9-21下午08:51:51
 * @param <S>
 */
public class JMSDetectItemWriteListener implements StepExecutionListener{

	private JmsTemplate jmsTemplate;
	
	@Override
	public void beforeStep(StepExecution stepExecution) {}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		int writeCount = 0;
		Object obj = jmsTemplate.receiveAndConvert();
		while(null != obj){
			writeCount++;
			CreditBill result = (CreditBill) obj;
			System.out.println("Receive from jms queue:"+result);
			obj = jmsTemplate.receiveAndConvert();
		}
		//Assert.assertEquals(stepExecution.getWriteCount(), writeCount);//判断写的条数是否和当前读的条数相等
		return stepExecution.getExitStatus();
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
