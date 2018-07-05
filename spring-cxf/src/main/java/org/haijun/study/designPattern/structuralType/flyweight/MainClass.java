package org.haijun.study.designPattern.structuralType.flyweight;

/**
 * 享元模式
 * 创建对象通过享元工厂从享元池获取，不创建重复对象，实现数据共享，节约空间
 * 意图：运用共享技术有效地支持大量细粒度的对象。
 * 主要解决：在有大量对象时，有可能会造成内存溢出，我们把其中共同的部分抽象出来，如果有相同的业务请求，直接返回在内存中已有的对象，避免重新创建。
 * 如何解决：用唯一标识码判断，如果在内存中有，则返回这个唯一标识码所标识的对象。
 * 关键代码：用 HashMap 存储这些对象。
 *
 */
public class MainClass {
	public static void main(String[] args) {
		TeacherFactory factory = new TeacherFactory();
		Teacher teacher1 = factory.getTeacher("0102034");
		Teacher teacher2 = factory.getTeacher("0102035");
		Teacher teacher3 = factory.getTeacher("0102034");
		Teacher teacher4 = factory.getTeacher("0102037");
		
		System.out.println(teacher1.getNumber());
		System.out.println(teacher2.getNumber());
		System.out.println(teacher3.getNumber());
		System.out.println(teacher4.getNumber());
		
		if(teacher1 == teacher3) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
}
