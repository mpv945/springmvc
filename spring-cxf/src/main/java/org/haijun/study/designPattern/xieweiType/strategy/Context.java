package org.haijun.study.designPattern.xieweiType.strategy;

/**
 * 核心，对策略进行引用（策略分装类或者叫策略容器类）
 */
public class Context {
	private Strategy strategy;
	
	public Context(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public double cost(double num){
		return this.strategy.cost(num);
	}
	
}
