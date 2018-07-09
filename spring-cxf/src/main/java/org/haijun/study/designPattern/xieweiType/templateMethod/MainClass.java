package org.haijun.study.designPattern.xieweiType.templateMethod;

/**
 * 模板方法模式
 * 意图：定义一个操作中的算法的骨架，而将一些步骤延迟到子类中。模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。
 * 主要解决：一些方法通用，却在每一个子类都重新写了这一方法。
 * 何时使用：有一些通用的方法。
 * 如何解决：将这些通用算法抽象出来。
 * 关键代码：在抽象类实现，其他步骤在子类实现。
 * 应用实例： 1、在造房子的时候，地基、走线、水管都一样，只有在建筑的后期才有加壁橱加栅栏等差异。
 */
public class MainClass {
	public static void main(String[] args) {
		MakeCar bus = new MakeBus();
//		bus.makeHead();
//		bus.makeBody();
//		bus.makeTail();
		bus.make();
		
		System.out.println("-------------------");
		
		MakeCar jeep = new MakeJeep();
//		jeep.makeHead();
//		jeep.makeBody();
//		jeep.makeTail();
		jeep.make();
		
		System.out.println("-------------------");
		
		MakeCar ka = new MakeKa();
		ka.make();
	}
}
