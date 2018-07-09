package org.haijun.study.designPattern.core.ocp.ex2;

/**
 * 开闭原则（Open Close Principle）:  对变化的封闭，对变化的不能封闭的应抽象隔离，对抽象隔离进行扩展设计，变化或者可能变化来的越早，抽象越容易
 * 开闭原则就是说对扩展开放，对修改关闭。在程序需要进行拓展的时候，不能去修改原有的代码，而是要扩展原有代码，实现一个热插拔的效果。
 * 所以一句话概括就是：为了使程序的扩展性好，易于维护和升级。想要达到这样的效果，我们需要使用接口和抽象类等，后面的具体设计中我们会提到这点。
 */
public class MainClass {
	public static void main(String[] args) {
		BankWorker bankWorker = new SavingBankWorker();
		bankWorker.operation();
		
		BankWorker bankWorker2 = new DrawingBankWorker();
		bankWorker2.operation();
		
		BankWorker bankWorker3 = new ZhuanZhangBankWorker();
		bankWorker3.operation();
		
		BankWorker bankWorker4 = new JiJinBankWorker();
		bankWorker4.operation();
	}
}
