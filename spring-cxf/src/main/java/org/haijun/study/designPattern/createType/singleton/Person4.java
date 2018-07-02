package org.haijun.study.designPattern.createType.singleton;

public class Person4 {
	private String name;
	private static Person4 person;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//构造函数私有化
	private Person4() {
	}

	//提供一个全局的静态方法
	// 这里的实现能够保证多线程并发下的线程安全性，但是这样的实现将全部的代码都被锁上了，同样的效率很低下。
	public static Person4 getPerson() {
		if(person == null) {
			synchronized (Person4.class) {
				if(person == null) {
					person = new Person4();
				}
			}

		}
		return person;
	}
}
