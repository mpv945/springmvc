package org.haijun.study.designPattern.createType.factoryMethod;

public class PearFactory implements FruitFactory {

	public Fruit getFruit() {
		return new Pear();
	}
}
