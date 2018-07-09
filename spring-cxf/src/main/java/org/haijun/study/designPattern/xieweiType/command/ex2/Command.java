package org.haijun.study.designPattern.xieweiType.command.ex2;

public abstract class Command {

	// 对调用抽象对象引用
	private Peddler peddler;
	
	
	public Command(Peddler peddler) {
		this.peddler = peddler;
	}
	
	public Peddler getPeddler() {
		return peddler;
	}

	public void setPeddler(Peddler peddler) {
		this.peddler = peddler;
	}

	/**
	 * 抽象抽象对象功能对象方法sail（）
	 */
	public abstract void sail();
}
