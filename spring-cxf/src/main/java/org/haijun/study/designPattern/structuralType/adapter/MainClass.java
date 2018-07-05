package org.haijun.study.designPattern.structuralType.adapter;

/**
 * 适配器模式
 * 意图：将一个类的接口转换成客户希望的另外一个接口。适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。
 * 主要解决：主要解决在软件系统中，常常要将一些"现存的对象"放到新的环境中，而新环境要求的接口是现对象不能满足的。
 * 如何解决：继承或依赖（推荐）适配包含被适配的引用。
 */
public class MainClass {
	public static void main(String[] args) {
//		Current current = new Current();
//		current.use220V();

//		Adapter adapter = new Adapter();
//		adapter.use18V();

		Adapter2 adapter = new Adapter2(new Current());
		adapter.use18V();
	}
}
