package org.haijun.study.designPattern.structuralType.bridgePattern.eg3;

/**
 * 桥接模式，把车（吉普，巴士）和 发动机安装（2000，2200）及行为分离。核心为car 引用发动机。
 * 意图：将抽象部分与实现部分分离，使它们都可以独立的变化。
 * 主要解决：在有多种可能会变化的情况下，用继承会造成类爆炸问题，扩展起来不灵活。
 */
public class MainClass {
	public static void main(String[] args) {
		
		Engine engine2000 = new Engine2000();// 引擎类型2000
		Engine engine2200 = new Engine2200();// 引擎类型2200
		
		Car car1 = new Bus(engine2000);// 安装2000的引擎到巴士
		car1.installEngine();
		
		Car car2 = new Bus(engine2200);// 安装2200的引擎到巴士
		car2.installEngine();
		
		Car jeep1 = new Jeep(engine2000);
		jeep1.installEngine();
		
		Car jeep2 = new Jeep(engine2200);
		jeep2.installEngine();
		
	}
}
