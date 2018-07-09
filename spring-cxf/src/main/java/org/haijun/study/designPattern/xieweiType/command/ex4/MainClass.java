package org.haijun.study.designPattern.xieweiType.command.ex4;

/**
 * 命令模式（（Peddler）把不同的行为拆成不同命令分装(Command的子类)invoker的引用Peddler，通过received（Waiter服务员）拥有Command对象参数化的调用不同的命令）
 * 意图：将一个请求封装成一个对象，从而使您可以用不同的请求对客户进行参数化。
 * 主要解决：在软件系统中，行为请求者与行为实现者通常是一种紧耦合的关系，但某些场合，比如需要对行为进行记录、撤销或重做、事务等处理时，这种无法抵御变化的紧耦合的设计就不太合适。
 * 何时使用：在某些场合，比如要对行为进行"记录、撤销/重做、事务"等处理，这种无法抵御变化的紧耦合是不合适的。
 * 如何解决：通过调用者调用接受者执行命令，顺序：调用者→接受者→命令。
 * 关键代码：定义三个角色：1、received 真正的命令执行对象 2、Command 3、invoker 使用命令对象的入口
 */
public class MainClass {
	public static void main(String[] args) {
		Peddler peddler = new Peddler(); // 商贩提供服务
//		peddler.sailApple();
//		peddler.sailBanana();

		Command appleCommand = new AppleCommand(peddler);// 通过命令执行不同的命令
		Command bananaCommand = new BananaCommand(peddler);
//		appleCommand.sail();
//		bananaCommand.sail();
		Waiter waiter = new Waiter();// 服务员作为代理方，执行不同命令

		//下订单
		waiter.setOrder(appleCommand);//给服务员下不同的命令，
		waiter.setOrder(bananaCommand);

		//移除订单某项
		waiter.removeOrder(appleCommand);

		waiter.sail();// 提供服务
	}
}
