package org.haijun.study.designPattern.xieweiType.command.ex2;

public class BananaCommand extends Command{

	public BananaCommand(Peddler peddler) {
		super(peddler);
	}

	/**
	 * 实现命令的抽象方法。可以增加格外的处理
	 */
	public void sail() {
		this.getPeddler().sailBanana();
	}

}
