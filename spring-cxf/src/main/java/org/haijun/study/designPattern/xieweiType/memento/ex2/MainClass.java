package org.haijun.study.designPattern.xieweiType.memento.ex2;

/**
 * 备忘录模式：保存一个对象的某个状态，以便在适当的时候恢复对象。备忘录模式属于行为型模式。
 * 意图：在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态。
 * 主要解决：所谓备忘录模式就是在不破坏封装的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，这样可以在以后将对象恢复到原先保存的状态。
 * 何时使用：很多时候我们总是需要记录一个对象的内部状态，这样做的目的就是为了允许用户取消不确定或者错误的操作，能够恢复到他原先的状态，使得他有"后悔药"可吃。
 * 如何解决：通过一个备忘录类专门存储对象状态。
 * 关键代码：客户不与备忘录类耦合，与备忘录管理类耦合。
 */
public class MainClass {
	public static void main(String[] args) {
		Person per = new Person("lifengxing","男",24);

//		Memento memento = per.createMemento();
		Caretaker caretaker = new Caretaker();
		caretaker.setMemento(per.createMemento());

		per.display();

		per.setName("beifeng");
		per.setSex("女");
		per.setAge(1);

		per.display();

		per.setMemento(caretaker.getMemento());
		per.display();

	}
}
