package org.haijun.study.designPattern.structuralType.decoratorPattern;

/**
 * 实现方式
 */
public class RunCar implements Car {

	public void run() {
		System.out.println("可以跑");
	}

	public void show() {
		this.run();
	}

}
