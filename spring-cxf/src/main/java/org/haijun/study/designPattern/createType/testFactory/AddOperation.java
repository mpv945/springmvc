package org.haijun.study.designPattern.createType.testFactory;

public class AddOperation extends Operation {

	public double getResult() {
		double result = this.getNum1() + this.getNum2();
		return result;
	}
}
