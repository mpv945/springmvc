package org.haijun.study.designPattern.xieweiType.state.ex3;

/**
 * 状态模式 ：类的行为是基于它的状态改变的。这种类型的设计模式属于行为型模式。（
 * 意图：允许对象在内部状态发生改变时改变它的行为，对象看起来好像修改了它的类。
 * 主要解决：对象的行为依赖于它的状态（属性），并且可以根据它的状态改变而改变它的相关行为。
 * 何时使用：代码中包含大量与对象状态有关的条件语句。
 * 如何解决：将各种具体的状态类抽象出来。
 * 关键代码：通常命令模式的接口中只有一个方法。而状态模式的接口中有一个或者多个方法。而且，状态模式的实现类的方法，一般返回值，或者是改变实例变量的值。
 * 应用实例： 1、打篮球的时候运动员可以有正常状态、不正常状态和超常状态。
 * 使用场景： 1、行为随状态改变而改变的场景。 2、条件、分支语句的代替者。
 */
public class MainClass {
	public static void main(String[] args) {
		Person person = new Person();
		
		person.setHour(7);
		person.doSomething();
		
		person.setHour(12);
		person.doSomething();
		
		person.setHour(18);
		person.doSomething();
		
		person.setHour(8);
		person.doSomething();
		
		person.setHour(7);
		person.doSomething();
		
		person.setHour(18);
		person.doSomething();
		
	}
}
