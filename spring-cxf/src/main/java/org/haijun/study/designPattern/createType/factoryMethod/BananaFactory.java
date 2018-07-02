package org.haijun.study.designPattern.createType.factoryMethod;

public class BananaFactory implements FruitFactory {

	public Fruit getFruit() {
		return new Banana();
	}

}
