package org.haijun.study.designPattern.core.lod.ex4;

/**
 * 迪米特法则（最少知道原则）
 * 就是说：一个类对自己依赖的类知道的越少越好。
 * 最少知道原则的另一个表达方式是：只与直接的朋友通信。类之间只要有耦合关系，就叫朋友关系。
 * 耦合分为依赖、关联、聚合、组合等。我们称出现为成员变量、方法参数、方法返回值中的类为直接朋友。
 * 局部变量、临时变量则不是直接的朋友。我们要求陌生的类不要作为局部变量出现在类中。
 */
public class MainClass {
	public static void main(String[] args) {
		SomeOne zhangsan = new SomeOne();
		zhangsan.setFriend(new Friend());
		zhangsan.setStranger(new StrangerA());// 这种直接依赖抽象陌生人，这样也符合迪米特法则。ex3是通过Friend 中间通信
		zhangsan.play();
	}
}
