package org.haijun.study.designPattern.createType.factoryMethod;

/**
 * 工厂方法是简单工厂的升级，更加符合开发关闭原则，新增一个新的产品，不在需要工厂就可以实现（封闭就是不要修改已完成的方法）
 *
 * 实现方式，新增产品工厂接口，产品产生通过产品工厂接口实现类完成。
 */
public class MainClass {
	public static void main(String[] args) {
		//获得AppleFactory
		FruitFactory ff = new AppleFactory();
		//通过AppleFactory来获得Apple实例对象
		Fruit apple = ff.getFruit();//
		apple.get();

		//获得BananaFactory
		FruitFactory ff2 = new BananaFactory();
		Fruit banana = ff2.getFruit();
		banana.get();

		//获得PearFactory
		FruitFactory ff3 = new PearFactory();
		Fruit pear = ff3.getFruit();
		pear.get();
	}
}
