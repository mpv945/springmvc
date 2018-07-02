package org.haijun.study.designPattern.createType.prototype;

import java.util.ArrayList;
import java.util.List;


public class MainClass {
	public static void main(String[] args) {
//		Person person1 = new Person();
//		person1.setName("lifengxing");
//		person1.setAge(30);
//		person1.setSex("男");
//
////		Person person2 = person1;
//		Person person2 = person1.clone();
//
//		System.out.println(person1.getName());
//		System.out.println(person1.getAge());
//		System.out.println(person1.getSex());
//
//		System.out.println(person2.getName());
//		System.out.println(person2.getAge());
//		System.out.println(person2.getSex());

		Person person1 = new Person();
		List<String> friends = new ArrayList<String>();
		friends.add("James");
		friends.add("Yao");

		person1.setFriends(friends);

		// 通过原型模式创建的对象，拥有的属性和原对象一样，
		Person person2 = person1.clone();

		System.out.println(person1.getFriends());
		System.out.println(person2.getFriends());

		friends.add("Mike");
		person1.setFriends(friends);
		System.out.println(person1.getFriends());
		System.out.println(person2.getFriends());
	}
}