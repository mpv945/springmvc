package org.haijun.study.designPattern.xieweiType.templateMethod;

/*
 * 组装车（通用的抽象方法，实现抽象方法）
 */
public abstract class MakeCar {
	//组装车头
	public abstract void makeHead();

	//组装车身
	public abstract void makeBody();

	//组装车尾
	public abstract void makeTail();

	/**
	 * 模板方法
	 */
	public void make() {
		this.makeHead();
		this.makeBody();
		this.makeTail();
	}
}
