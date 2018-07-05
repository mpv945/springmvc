package org.haijun.study.designPattern.structuralType.proxy;

import java.lang.reflect.Proxy;

/**
 * 动态代理模式
 * 意图：为其他对象提供一种代理以控制对这个对象的访问。
 * 何时使用：想在访问一个类时做一些控制。
 * 如何解决：增加中间层。
 * 关键代码：实现与被代理类组合。
 */
public class MainClass {
	public static void main(String[] args) {
		RealSubject realSubject = new RealSubject();
		MyHandler myHandler = new MyHandler();
		myHandler.setRealSubject(realSubject);
		
		Subject proxySubject = (Subject)Proxy.newProxyInstance(RealSubject.class.getClassLoader(), realSubject.getClass().getInterfaces(), myHandler);
		proxySubject.sailBook();
	}
}
