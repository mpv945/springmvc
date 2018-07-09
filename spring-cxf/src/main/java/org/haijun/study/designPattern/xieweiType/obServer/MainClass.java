package org.haijun.study.designPattern.xieweiType.obServer;

/**
 * 观察者模式（（被观察的对象）当一个对象发生变化时，可以通知关联对象发生反应同步，其他对象为观察者）
 * 意图：定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。
 * 主要解决：一个对象状态改变给其他对象通知的问题，而且要考虑到易用和低耦合，保证高度的协作。
 * 何时使用：一个对象（目标对象）的状态发生改变，所有的依赖对象（观察者对象）都将得到通知，进行广播通知。
 * 如何解决：使用面向对象技术，可以将这种依赖关系弱化。
 * 应用实例： 1、拍卖的时候，拍卖师观察最高标价，然后通知给其他竞价者竞价。
 */
public class MainClass {
	public static void main(String[] args) {
		BlogUser user = new BlogUser();
		user.addObserver(new MyObServer());// 注册观察者，可以注册多个观察者
		//user.deleteObservers();//删除观察者
		user.publishBlog("哈哈，博客上线了", "大家多来访问");
	}
}

