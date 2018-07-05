package org.haijun.study.designPattern.structuralType.decoratorPattern;

/**
 * 装饰模式
 * IO流的FileRead和BufferRead。在读取文件情况下，通过BufferRead 装饰一个缓存的功能
 * 意图：动态地给一个对象添加一些额外的职责。就增加功能来说，装饰器模式相比生成子类更为灵活。
 * 主要解决：一般的，我们为了扩展一个类经常使用继承方式实现，由于继承为类引入静态特征，并且随着扩展功能的增多，子类会很膨胀。
 * 何时使用：在不想增加很多子类的情况下扩展类。
 * 如何解决：将具体功能职责划分，同时继承装饰者模式。
 * 关键代码： 1、Car 类充当抽象角色，不应该具体实现。 2、修饰类CarDecorator引用和实现 Car ，具体扩展类重写父类方法和新增功能方法。
 */
public class MainClass {
	public static void main(String[] args) {
		// 汽车的基本功能实现
		Car car = new RunCar();
		car.show();
		System.out.println("---------");

		// 通过装饰模式为基础的汽车功能增加游的功能
		Car swimcar = new SwimCarDecorator(car);
		swimcar.show();
		System.out.println("---------");

		// 通过装饰模式为成型的汽车再增加飞的功能
		Car flySwimCar = new FlyCarDecorator(swimcar);
		flySwimCar.show();
	}
}
