package org.haijun.study.designPattern.structuralType.bridgePattern.eg3;

/**
 * 抽象车
 */
public abstract class Car {

	/**
	 * 引用行为
	 */
	private Engine engine;
	
	public Car(Engine engine) {
		this.engine = engine;
	}
	
	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	/**
	 * 抽象具体行为，让具体车型去实现
	 */
	public abstract void installEngine();
}
