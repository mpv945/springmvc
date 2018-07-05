package org.haijun.study.designPattern.structuralType.adapter;

public class Adapter2 {

	// 包含被适配的引用
	private Current current;

	public Adapter2(Current current) {
		this.current = current;
	}

	public void use18V() {
		System.out.println("使用适配器");
		this.current.use220V();
	}
}
