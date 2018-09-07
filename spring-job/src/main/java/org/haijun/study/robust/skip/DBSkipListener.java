/**
 * 
 */
package org.haijun.study.robust.skip;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * SkipListener 接口默认实现
 * 		CompositeSkipListener 组合跳过拦截器实现，可以定义一组跳过拦截器，依照顺序执行
 * 		MulticasterBatchListener 同时实现StepExecutionListener, ChunkListener, ItemReadListener<T>,
 * 			ItemProcessListener<T, S>, ItemWriteListener<S>, SkipListener<T, S>,
 * 			RetryReadListener, RetryProcessListener, RetryWriteListener接口组合策略的拦截器
 * 		SkipListenerSupport 跳过处理器的默认实现，继承该类，重新自己关系的方法即可
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-29上午08:02:00
 */
public class DBSkipListener implements SkipListener<String, String> {
	private JdbcTemplate jdbcTemplate;

	// 注意skip拦截器并发在读，处理，写阶段发生异常以后立刻执行，而是在批操作事务提交正确之前才执行，
	// 在读阶段发生跳过（可以不用实现接口，直接定义类，在类方法使用@OnSkipInRead注解）
	public void onSkipInRead(Throwable t) {
		if (t instanceof FlatFileParseException) {
			jdbcTemplate.update("insert into skipbills "
					+ "(line,content) values (?,?)",
					((FlatFileParseException) t).getLineNumber(),
					((FlatFileParseException) t).getInput());
		}
	}

	//在写阶段发生异常并且配置了异常可以跳过的时候触发该操作
	public void onSkipInWrite(String item, Throwable t) {
		// TODO Auto-generated method stub
	}

	public void onSkipInProcess(String item, Throwable t) {
		// TODO Auto-generated method stub
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
