package org.haijun.study.designPattern.core.srp;


public class MainClass {
	public static void main(String[] args) {
		//接受客户端的输入
		SaveToDB std = new SaveToDB();
		std.shuru();
		if(std.validate()) {
			std.getConnection();
			std.save();
		}
	}
}
