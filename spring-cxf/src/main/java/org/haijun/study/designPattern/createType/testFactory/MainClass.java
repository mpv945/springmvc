package org.haijun.study.designPattern.createType.testFactory;

import java.util.Scanner;

public class MainClass {
	public static void main(String[] args) {
		//1.接受控制台输入
		System.out.println("---计算器程序---");
		System.out.println("输入第一个操作数");
		Scanner scanner = new Scanner(System.in);
		String strNum1 = scanner.nextLine();

		System.out.println("输入运算符");
		String oper = scanner.nextLine();

		System.out.println("输入第二个操作数");
		String strNum2 = scanner.nextLine();
		double result = 0;
		double num1 = Double.parseDouble(strNum1);
		double num2 = Double.parseDouble(strNum2);


		//1.使用简单工厂模式（好扩展，但是没有符合关闭原则，新增计算方式需要修改工作的代码。
/*		Operation operation1 = OperationFactory.getOperation(oper);// 实体类，定义静态的获取操作方法，根据不同的运算符返回不同操作类
		operation1.setNum1(num1);
		operation1.setNum2(num2);
		operation1.getResult();//计算*/
		//2.进行运算
		if("+".equals(oper)) {
			OperationFactory factory = new AddOperationFactory();
			Operation operation = factory.getOperation();
			operation.setNum1(num1);
			operation.setNum2(num2);
			result = operation.getResult();
		}

		//3.返回结果
		System.out.println(strNum1 + oper + strNum2 + "=" + result);
	}
}
