package org.haijun.study.designPattern.xieweiType.coR.ext2;

public class CarTailHandler extends CarHandler{

	public void HandlerCar() {
		System.out.println("组装车尾");
		if(this.carHandler != null) {
			this.carHandler.HandlerCar();
		}
	}

}
