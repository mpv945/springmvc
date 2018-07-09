package org.haijun.study.designPattern.xieweiType.visitor;

/**
 * 访问者模式（分离对象的数据和行为）
 * 意图：主要将数据结构与数据操作分离。
 * 主要解决：稳定的数据结构和易变的操作耦合问题。
 * 何时使用：需要对一个对象结构中的对象进行很多不同的并且不相关的操作，而需要避免让这些操作"污染"这些对象的类，使用访问者模式将这些封装到类中。
 * 如何解决：在被访问的类里面加一个对外提供接待访问者的接口。
 * 关键代码：在数据基础类里面有一个方法接受访问者，将自身引用传入访问者。
 * 应用实例：您在朋友家做客，您是访问者，朋友接受您的访问，您通过朋友的描述，然后对朋友的描述做出一个判断，这就是访问者模式。
 * 优点： 1、符合单一职责原则。 2、优秀的扩展性。 3、灵活性。
 * 使用场景： 1、对象结构中对象对应的类很少改变，但经常需要在此对象结构上定义新的操作。
 */
public class MainClass {
	public static void main(String[] args) {
		Park park = new Park();
		park.setName("越秀公园");
		VisitorA visitorA = new VisitorA();

		park.accept(visitorA);

		VisitorB visitorB = new VisitorB();
		park.accept(visitorB);

		VisitorManager visitorManager = new VisitorManager();
		park.accept(visitorManager);
	}
}
