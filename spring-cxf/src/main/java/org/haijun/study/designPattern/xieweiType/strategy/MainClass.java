package org.haijun.study.designPattern.xieweiType.strategy;

/**
 * 策略模式（装饰模式有点类似）
 * 意图：定义一系列的算法,把它们一个个封装起来, 并且使它们可相互替换。
 * 主要解决：在有多种算法相似的情况下，使用 if...else 所带来的复杂和难以维护。
 * 何时使用：一个系统有许多许多类，而区分它们的只是他们直接的行为。
 * 如何解决：将这些算法封装成一个一个的类，任意地替换。
 */
public class MainClass {
	public static void main(String[] args) {
		double num = 200;
		Context context = new Context(new StrategyB());
		double newNum = context.cost(num);
		System.out.println("实际付账" + newNum + "元");
	}
}
