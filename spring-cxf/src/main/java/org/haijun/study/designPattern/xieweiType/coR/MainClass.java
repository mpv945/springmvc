package org.haijun.study.designPattern.xieweiType.coR;

/**
 * 未使用责任链模式实现： 将一个任务分成几个步骤。一个任务分成多个部分（多个任务）汽车组装：分为组装车头，车身，车尾
 */
public class MainClass {
	public static void main(String[] args) {
		CarHandler headH = new CarHeadHandler();
		CarHandler bodyH = new CarBodyHandler();
		CarHandler tailH = new CarTailHandler();
		
		headH.HandlerCar();
		bodyH.HandlerCar();
		tailH.HandlerCar();
	}
}
