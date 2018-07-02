package org.haijun.study.designPattern.createType.singleton;

/**
 * 线程安全的懒汉式单例
 * 方法中声明synchronized关键字
 */
public class Person3 {
	private String name;
	private static Person3 person;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//构造函数私有化
	private Person3() {
	}

	//提供一个全局的静态方法，使用同步方法
	//但是这种实现方式的运行效率会很低。同步方法效率低，那我们考虑使用同步代码块来实现：看第四例
	public static synchronized Person3 getPerson() {
		if(person == null) {
			person = new Person3();
		}
		return person;
	}
}
