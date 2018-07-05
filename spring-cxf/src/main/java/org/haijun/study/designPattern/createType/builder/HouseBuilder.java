package org.haijun.study.designPattern.createType.builder;

/*
 * 工程队(抽象修房子的动作接口)
 */
public interface HouseBuilder {
	//修地板
	public void makeFloor();
	//修墙
	public void makeWall();
	//修屋顶
	public void makeHousetop();
	public House getHouse();
}
