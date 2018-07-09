package org.haijun.study.designPattern.core.ocp;

public class MainClass {
	public static void main(String[] args) {
		BankWorker bankWorker = new BankWorker();
		//存款
		bankWorker.saving();

		//取款
		bankWorker.drawing();

		//转账
		bankWorker.zhuanzhang();

		//基金
		bankWorker.jijin();
	}
}
