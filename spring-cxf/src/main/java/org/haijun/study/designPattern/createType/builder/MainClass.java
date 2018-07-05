package org.haijun.study.designPattern.createType.builder;

public class MainClass {

	public static void main(String[] args) {
//		//客户直接造房子
//		House house = new House();
//		house.setFloor("地板");
//		house.setWall("墙");
//		house.setHousetop("屋顶");

		// 建造者模式（Builder Pattern）使用多个简单的对象一步一步构建成一个复杂的对象。
		// 这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。
		//应用实例： 1、去肯德基，汉堡、可乐、薯条、炸鸡翅等是不变的，而其组合是经常变化的，生成出所谓的"套餐"。 2、JAVA 中的 StringBuilder。
		//意图：将一个复杂的构建与其表示相分离，使得同样的构建过程可以创建不同的表示。
		//由工程队来修(指定公寓创建队来修)
		HouseBuilder builder = new GongyuBuilder();
		//设计者来做（房屋设计开始）
		HouseDirector director = new HouseDirector();
		director.makeHouse(builder);//设计人员指定使用那个工程队

		House house = builder.getHouse();//最后修建好房子就是自己需要的房子
		System.out.println(house.getFloor());
		System.out.println(house.getWall());
		System.out.println(house.getHousetop());
	}

}
