package org.haijun.study.designPattern.structuralType.proxy.team2;

/**
 * 代理主题
 */
public class ProxySubject implements Subject{
	private RealSubject realSubject;

	public void sailBook() {
		dazhe();
		if(realSubject == null) {
			realSubject = new RealSubject();
		}
		realSubject.sailBook();
		give();
	}

	public void dazhe() {
		System.out.println("打折");
	}

	public void give() {
		System.out.println("赠送代金券");
	}
}
