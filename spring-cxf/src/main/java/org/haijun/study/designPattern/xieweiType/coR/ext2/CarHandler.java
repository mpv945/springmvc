package org.haijun.study.designPattern.xieweiType.coR.ext2;

/**
 * 抽象组装车功能（为创建不同的车定义抽象）
 */
public abstract class CarHandler {
	//任链中的下一个元素
	protected CarHandler carHandler;
	public CarHandler setNextHandler(CarHandler carHandler) {
		this.carHandler = carHandler;
		return this.carHandler;
	}

	public abstract void HandlerCar();
}
