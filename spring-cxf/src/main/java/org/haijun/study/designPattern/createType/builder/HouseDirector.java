package org.haijun.study.designPattern.createType.builder;

public class HouseDirector {

	public void makeHouse(HouseBuilder builder) {
		builder.makeFloor();
		builder.makeWall();
		builder.makeHousetop();
	}

}
