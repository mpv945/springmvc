package org.haijun.study.designPattern.xieweiType.state;

public class Person {
	private int hour;

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public void doSomething(){
		if(hour == 7) {
			System.out.println("吃早餐");
		} else if(hour == 12) {
			System.out.println("吃中饭");
		} else if(hour == 18) {
			System.out.println("吃晚饭");
		} else {
			System.out.println(hour + "未定义");
		}
	}
}
