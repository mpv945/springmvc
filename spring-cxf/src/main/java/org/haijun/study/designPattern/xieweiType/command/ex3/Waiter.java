package org.haijun.study.designPattern.xieweiType.command.ex3;

/**
 * 服务员
 */
public class Waiter {
	private Command command;

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public void sail() {
		command.sail();
	}
}
