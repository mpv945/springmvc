package org.haijun.study.designPattern.xieweiType.state.ex3;

public class NoState extends State {

	public void doSomething(Person person) {
		System.out.println(person.getHour() +  "未定义");
	}

}
