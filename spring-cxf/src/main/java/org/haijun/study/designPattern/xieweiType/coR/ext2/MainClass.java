package org.haijun.study.designPattern.xieweiType.coR.ext2;

/**
 * 责任链模式（有序的链） 第二种（一个任务分成多个部分（多个任务）汽车组装：分为组装车头，车身，车尾）
 * 意图：避免请求发送者与接收者耦合在一起，让多个对象都有可能接收请求，将这些对象连接成一条链，并且沿着这条链传递请求，直到有对象处理它为止。
 * 主要解决：职责链上的处理者负责处理请求，客户只需要将请求发送到职责链上即可，无须关心请求的处理细节和请求的传递，所以职责链将请求的发送者和请求的处理者解耦了。
 * 何时使用：在处理消息的时候以过滤很多道。
 * 如何解决：拦截的类都实现统一接口。
 * 关键代码：Handler 里面聚合它自己，在 HanleRequest 里判断是否合适，如果没达到条件则向下传递，向谁传递之前 set 进去。
 */
public class MainClass {
	public static void main(String[] args) {
		CarHandler headH = new CarHeadHandler();
		CarHandler bodyH = new CarBodyHandler();
		CarHandler tailH = new CarTailHandler();

		//组装顺序预先设定好,顺序是车头-->车身-->车尾
		headH.setNextHandler(bodyH);
		bodyH.setNextHandler(tailH);

		//调用职责链的链头来完成操作
		headH.HandlerCar();

		System.out.println("---------------");

		//顺序改变，希望是车身-->车头--车尾
		bodyH.setNextHandler(headH);
		headH.setNextHandler(tailH);
		bodyH.HandlerCar();

		//简便操作
		bodyH.setNextHandler(headH).setNextHandler(tailH);
		bodyH.HandlerCar();


	}
}
