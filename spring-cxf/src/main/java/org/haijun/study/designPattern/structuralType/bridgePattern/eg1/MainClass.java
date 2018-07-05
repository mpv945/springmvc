package org.haijun.study.designPattern.structuralType.bridgePattern.eg1;

/**
 * 实现方式一，增加一个新车型，需要增加很多发动机规格的车
 *
 */
public class MainClass {
	public static void main(String[] args) {
		Car car1 = new Bus2000();
		car1.installEngine();
	}
}
