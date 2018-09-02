/**
 * 
 */
package org.haijun.study.item;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * 作业终止拦截器（不要在）
 * 2013-3-20下午10:19:47
 */
public class StopStepListener<T> implements StepExecutionListener, ItemReadListener<T> {
	private StepExecution stepExecution;
	private Boolean stop = Boolean.FALSE;

	// 作业步执行前
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("获取stepExecution（作业步的执行器对象），并保存在拦截器的实例中");
		this.stepExecution = stepExecution;
	}

	// 读组件执行前
	public void beforeRead() {
		if(isStop()) {
			// stepExecution.setTerminateOnly()会发送一个停止消息给框架，一旦框架收到消息，获取该作业的控制权会自动停止作业
			System.out.println("开始准备读了，但是收到停止指令，我先给框架发送一个停止消息告诉框架");
			this.stepExecution.setTerminateOnly();
		}
	}

	// stop执行之后
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}

	// 读组件执行之后
	public void afterRead(T item) {}

	// 读发送异常
	public void onReadError(Exception ex) {}

	public Boolean isStop() {
		return stop;
	}
	
	public void setStop(Boolean stop) {
		this.stop = stop;
	}
}
