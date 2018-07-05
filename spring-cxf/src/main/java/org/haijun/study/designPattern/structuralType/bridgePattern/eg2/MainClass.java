package org.haijun.study.designPattern.structuralType.bridgePattern.eg2;

/**
 * 方式2 ，抽象引擎规格，把子类安装不同规格的方法抽象到接口。但是违反了扩展开放和修改关闭的远程。
 */
public class MainClass {
	public static void main(String[] args) {
		Car car1 = new Bus();
		car1.install2000Engine();
	}
}
