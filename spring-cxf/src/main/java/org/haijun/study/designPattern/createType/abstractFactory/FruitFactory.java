package org.haijun.study.designPattern.createType.abstractFactory;

public interface FruitFactory {
	//实例化Apple
	public Fruit getApple();
	//实例化Banana
	public Fruit getBanana();
}
