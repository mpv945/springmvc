package org.haijun.study.designPattern.createType.simpleFactory;

/**
 * 工厂模式（Factory Pattern）是 Java 中最常用的设计模式之一。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。
 * 在工厂模式中，我们在创建对象时不会对客户端暴露创建逻辑，并且是通过使用一个共同的接口来指向新创建的对象。
 * 例如：Hibernate 换数据库只需换方言和驱动就可以。
 *
 * 实现：客服端通过指定给工厂不同的参数返回不同的实现接口实例
 */
public class MainClass {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
//		//实例化一个Apple
//		Apple apple = new Apple();
//		//实例化一个Banana
//		Banana banana = new Banana();
//
//		apple.get();
//		banana.get();

//		//实例化一个Apple,用到了多态
//		Fruit apple = new Apple();
//		Fruit banana = new Banana();
//		apple.get();
//		banana.get();

//		//实例化一个Apple
//		Fruit apple = FruitFactory.getApple();
//		Fruit banana = FruitFactory.getBanana();
//		apple.get();
//		banana.get();

		Fruit apple = FruitFactory.getFruit("org.haijun.study.designPattern.createType.simpleFactory.Apple");
		Fruit banana = FruitFactory.getFruit("org.haijun.study.designPattern.createType.simpleFactory.Banana");
		apple.get();
		banana.get();

	}
}
