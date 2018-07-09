package org.haijun.study.designPattern.core.lsp.ex3;

/**
 * 里氏替换原则（多态的一种，父类引用指向自类对象）（Liskov Substitution Principle），只有当衍生类可以替换掉基类，软件单位的功能不受到影响时，
 * 基类才能真正被复用，而衍生类也能够在基类的基础上增加新的行为。
 * 里氏代换原则中说，任何基类可以出现的地方，子类一定可以出现。（反过来是不成立）
 * 里氏代换原则是对“开-闭”原则的补充。实现“开-闭”原则的关键步骤就是抽象化。
 */
public class MainClass {
	public static void main(String[] args) {
		ChangFX changfx = new ChangFX();
		changfx.setHeight(10);
		changfx.setWidth(20);
		test(changfx);
		
		ZhengFX zhengfx = new ZhengFX();
		zhengfx.setHeight(10);
		test(zhengfx);
	}
	
	public static void test(Sibianxing sibianxing) {
		System.out.println(sibianxing.getHeight());
		System.out.println(sibianxing.getWidth());
	}
	
//	public static void resize(Sibianxing sibianxing) {
//		while(sibianxing.getHeight() <= sibianxing.getWidth()) {
//			sibianxing.setHeight(sibianxing.getHeight() + 1);
//			test(sibianxing);
//		}
//	}
}
