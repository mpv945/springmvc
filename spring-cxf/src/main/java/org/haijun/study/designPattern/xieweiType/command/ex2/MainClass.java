package org.haijun.study.designPattern.xieweiType.command.ex2;

public class MainClass {
	public static void main(String[] args) {
		Peddler peddler = new Peddler();// 可以把商贩卖香蕉和，卖苹果通过命令模式拆分成不能的命令
//		peddler.sailApple();
//		peddler.sailBanana();
		
		Command appleCommand = new AppleCommand(peddler);
		Command bananaCommand = new BananaCommand(peddler);
		appleCommand.sail();
		bananaCommand.sail();
	}
}
