package org.haijun.study.designPattern.xieweiType.memento.ex2;

/**
 * 1、为了符合迪米特原则，还要增加一个管理备忘录的类。
 * 2、为了节约内存，可使用原型模式+备忘录模式。
 */
public class Caretaker {
	private Memento memento;

	public Memento getMemento() {
		return memento;
	}

	public void setMemento(Memento memento) {
		this.memento = memento;
	}
	
	
}
