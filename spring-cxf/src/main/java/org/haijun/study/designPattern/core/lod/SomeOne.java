package org.haijun.study.designPattern.core.lod;

/**
 * 某人
 */
public class SomeOne {
	// 真实的方法上面和下面方法名不一样的
	public void play(Friend friend){
		System.out.println("通过第三者找朋友 玩");
		friend.play();
	}
	
	public void play(Stranger stranger) {
		System.out.println("通过第三者找陌生人 玩");
		stranger.play();
	}
}
