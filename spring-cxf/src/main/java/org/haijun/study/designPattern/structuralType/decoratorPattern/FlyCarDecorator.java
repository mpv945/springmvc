package org.haijun.study.designPattern.structuralType.decoratorPattern;

public class FlyCarDecorator extends CarDecorator{

	public FlyCarDecorator(Car car) {
		super(car);
	}

	public void show() {
		this.getCar().show();
		this.fly();
	}

	public void fly() {
		//System.out.println("可以飞");
		this.run();
	}

	public void run() {
		System.out.println("可以飞");
	}
}
