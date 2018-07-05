package org.haijun.study.designPattern.structuralType.decoratorPattern;

/**
 * 汽车装饰类（1.实现功能，2，包含核心对象。）
 */
public abstract class CarDecorator implements Car{

	// 保持原有的功能
	private Car car;
	
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public CarDecorator(Car car) {
		this.car = car;
	}
	
	public abstract void show();
}
