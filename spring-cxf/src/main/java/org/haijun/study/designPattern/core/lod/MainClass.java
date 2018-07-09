package org.haijun.study.designPattern.core.lod;


public class MainClass {
	public static void main(String[] args) {
		SomeOne zhangsan = new SomeOne();
		zhangsan.play(new Friend());
		zhangsan.play(new Stranger());
	}
}
