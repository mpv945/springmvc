package org.haijun.study.designPattern.core.dip;

/**
 * 依赖倒转原则（Dependence Inversion Principle）
 * 这个是开闭原则的基础，具体内容：面向接口编程，依赖于抽象而不依赖于具体。写代码时用到具体类时，不与具体类交互，而与具体类的上层接口交互。
 * 实现层依赖于抽象层（以前是高层模块依赖底层模块）
 * 1.抽象不应该依赖于细节，细节应该依赖于抽象
 * 2.高层模块不应该依赖于底层模块，两者应都依赖抽象
 *
 */
public class MainClass {
	public static void main(String[] args) {
		Computer computer = new Computer();
		computer.setMainBoard(new HuaSuoMainBoard());
		computer.setMemory(new JinShiDunMemory());
		computer.setHarddisk(new XiJieHardDisk());
		
		computer.display();
		
		System.out.println("-------------");
		
		computer.setMainBoard(new WeiXingMainBoard());
		computer.display();
	}
}
