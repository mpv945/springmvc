package org.haijun.study.designPattern.createType.factoryMethod;

public class AppleFactory implements FruitFactory {

	public Fruit getFruit() {
		return new Apple();
	}

}
