/**
 * 
 */
package org.haijun.study.robust.retry.template;

import org.springframework.classify.Classifier;
import org.springframework.classify.ClassifierSupport;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.RetryState;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;

/**
 * 自定义重试模板，通过系统实现RetryOperations接口的RetryTemplate来完成自定义重试模板功能
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-21下午11:00:35
 */
public class RetryTemplateExample { //  implements RetryOperations
	
	/**
	 *  根据次数重试
	 */
	public static void retrySimpleRetry(){
		RetryCallback<String> retryCallback = new DefaultRetryCallback();
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(3);
		retry(retryCallback, retryPolicy, null, null);
	}
	
	/**
	 * 根据次数重试并且自动恢复
	 */
	public static void retrySimpleRetryAndRecovery(){
		RetryCallback<String> retryCallback = new DefaultRetryCallback();
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(5);
		RecoveryCallback<String> recoveryCallback = new DefaultRecoveryCallback<String>();
		retry(retryCallback, retryPolicy, recoveryCallback, null);
	}
	
	/**
	 * 根据时间范围内重试
	 */
	public static void retryTimeout(){
		RetryCallback<String> retryCallback = new DefaultRetryCallback();
		TimeoutRetryPolicy retryPolicy = new TimeoutRetryPolicy();
		retryPolicy.setTimeout(3000L);
		retry(retryCallback, retryPolicy, null, null);
	}
	
	/**
	 * 3秒范围内重试
	 * 逻辑执行后休息500ms
	 * 重试后，休息500ms
	 * 总执行次数在3次左右
	 */
	public static void retryTimeoutAndRecoveryAndBackOff(){
		RetryCallback<String> retryCallback = new DefaultRetryCallback();
		TimeoutRetryPolicy retryPolicy = new TimeoutRetryPolicy();
		retryPolicy.setTimeout(3000L);
		BackOffPolicy backOffPolicy = new DefaultBackoffPolicy();
		RecoveryCallback<String> recoveryCallback = new DefaultRecoveryCallback<String>();
		retry(retryCallback, retryPolicy, recoveryCallback, backOffPolicy);
	}
	
	/**
	 *  通过系统实现的RetryTemplate来配置和运行重试模板的重试操作
	 * @param retryCallback
	 * @param retryPolicy
	 * @param recoveryCallback
	 * @param backOffPolicy
	 */
	public static void retry(RetryCallback<String> retryCallback, RetryPolicy retryPolicy, RecoveryCallback<String>
			recoveryCallback, BackOffPolicy backOffPolicy){
		boolean retryStateFlg = true;// 是否需要带状态重试
		RetryTemplate template = new RetryTemplate();
		template.setRetryPolicy(retryPolicy);
		// 通过系统实现RetryOperations 接口类RetryTemplate来执行重试
		if(null != backOffPolicy){
			template.setBackOffPolicy(backOffPolicy);//设置业务补偿
		}

		try {
			//实现RetryOperations 接execute方法有四个重载
			// 参数带RetryState这个都表示是有状态重试
			if(retryStateFlg){
				// 定义异常筛选类，使用本框架提供的support类，对所有类型的异常都会返回false，
				// false表示重试逻辑发生任何异常的时候都不会导致rollback，而是执行重试操作，
				Classifier<? super Throwable, Boolean> classifier = new ClassifierSupport<>(Boolean.FALSE);
				// 定义retryState，使用key来作为唯一关键字，将RetryContext通过关键字“key”缓存到map中，后续重试的时候都能通过该关键字获取RetryContext
				RetryState retryState = new DefaultRetryState("key", false, classifier);
				template.execute(retryCallback, recoveryCallback,retryState);//执行有状态的重试
			}else{
				template.execute(retryCallback, recoveryCallback);//执行重试
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		retryTimeout();
//		retrySimpleRetry();
//		retrySimpleRetryAndRecovery();
		retryTimeoutAndRecoveryAndBackOff();
	}


}
