package org.haijun.study.designPattern.core.lod.ex2;

public class Friend {
	public void play(){
		System.out.println("friends paly");
	}
	
	public Stranger getStranger() {
		return new Stranger();
	}
}
