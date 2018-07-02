package org.haijun.study.designPattern.createType.singleton;

/**
 * 懒汉式单例
 * 懒汉式单例是指在方法调用获取实例时才创建实例，因为相对饿汉式显得“不急迫”，所以被叫做“懒汉模式”。
 */
public class Person2 {
	private String name;
	private static Person2 person;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//构造函数私有化
	private Person2() {
	}

	//提供一个全局的静态方法
	public static Person2 getPerson() {
		if(person == null) {
			person = new Person2();
		}
		return person;
	}
}
