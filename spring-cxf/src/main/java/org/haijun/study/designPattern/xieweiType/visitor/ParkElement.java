package org.haijun.study.designPattern.xieweiType.visitor;

/*
 * 公园每一部分的抽象（例如公园具体由a和b部分）
 */
public interface ParkElement {
	//用来接纳访问者
	public void accept(Visitor visitor);
}
