package org.haijun.study.designPattern.xieweiType.mediator.ex1;

public abstract class Person {
	private String name; //名字
	private int condition;// 个人条件
	
	public Person(String name, int condition) {
		this.name = name;
		this.condition = condition;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCondition() {
		return condition;
	}
	
	public void setCondition(int condition) {
		this.condition = condition;
	}

	/**
	 * 找到另一半
	 * @param person
	 */
	public abstract void getPartner(Person person);
	
}
