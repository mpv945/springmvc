package org.haijun.study.designPattern.core.lod.ex3;

public class Friend {
	public void play(){
		System.out.println("friends paly");
	}
	
	public void playWithStranger() {
		Stranger stranger = new Stranger();
		stranger.play();
	}
}
