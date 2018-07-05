package org.haijun.study.designPattern.createType.builder;

/*
 * 公寓工程队
 */
public class GongyuBuilder implements HouseBuilder{
	House house = new House();

	public House getHouse() {
		return house;
	}

	public void makeFloor() {
		house.setFloor("公寓-->地板");
	}

	public void makeHousetop() {
		house.setHousetop("公寓-->房顶");
	}

	public void makeWall() {
		house.setWall("公寓-->墙");
	}

}