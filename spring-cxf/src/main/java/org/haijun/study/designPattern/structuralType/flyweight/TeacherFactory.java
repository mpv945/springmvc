package org.haijun.study.designPattern.structuralType.flyweight;

import java.util.HashMap;
import java.util.Map;

//享元工厂
public class TeacherFactory {
	private Map<String,Teacher> pool;//享元池
	
	public TeacherFactory() {
		pool = new HashMap<String,Teacher>();
	}
	
	public Teacher getTeacher(String number) {
		Teacher teacher = pool.get(number);
		if(teacher == null) {
			teacher = new Teacher();
			teacher.setNumber(number);
			pool.put(number, teacher);
		}
		return teacher;
	}
}
