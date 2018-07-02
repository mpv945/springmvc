package org.haijun.study.designPattern.createType.singleton;

/**
 * 饿汉式单例
 * 饿汉式单例是指在方法调用前，实例就已经创建好了。
 */
public class Person {
	public static final Person person = new Person();
	private String name;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//构造函数私有化
	private Person() {
	}

	//提供一个全局的静态方法
	public static Person getPerson() {
		return person;
	}
}
