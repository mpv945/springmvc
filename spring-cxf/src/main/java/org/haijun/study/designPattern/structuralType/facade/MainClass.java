package org.haijun.study.designPattern.structuralType.facade;

/**
 * 外观模式
 * 意图：为子系统中的一组接口提供一个一致的界面，外观模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。
 * 主要解决：降低访问复杂系统的内部子系统时的复杂度，简化客户端与之的接口。
 * 何时使用： 1、客户端不需要知道系统内部的复杂联系，整个系统只需提供一个"接待员"即可。 2、定义系统的入口。
 */
public class MainClass {
	public static void main(String[] args) {
//		//80年代，基金出现之前
//		Gupiao gupiao = new Gupiao();
//		gupiao.mai();
//
//		Qihuo qihuo = new Qihuo();
//		qihuo.chao();
//
//		GuoZai guozhai = new GuoZai();
//		guozhai.mai();
		//有了基金之后
//		JiJin jijin = new JiJin();
//		jijin.maiJijinA();
		JiJin jijin = new JiJin();
		jijin.maiJijinB();
	}
}
