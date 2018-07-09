package org.haijun.study.designPattern.xieweiType.interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * 解释器模式（使用很少，知道原理就好）
 * 提供了评估语言的语法或表达式的方式，它属于行为型模式。这种模式实现了一个表达式接口，该接口解释一个特定的上下文。这种模式被用在 SQL 解析、符号处理引擎等。
 * 意图：给定一个语言，定义它的文法表示，并定义一个解释器，这个解释器使用该标识来解释语言中的句子。
 * 主要解决：对于一些固定文法构建一个解释句子的解释器。
 * 何时使用：如果一种特定类型的问题发生的频率足够高，那么可能就值得将该问题的各个实例表述为一个简单语言中的句子。
 * 如何解决：构件语法树，定义终结符与非终结符。
 * 关键代码：构件环境类，包含解释器之外的一些全局信息，一般是 HashMap。
 * 应用实例：编译器、运算表达式计算。
 * 优点： 1、可扩展性比较好，灵活。 2、增加了新的解释表达式的方式。 3、易于实现简单文法。
 */
public class MainClass {
	public static void main(String[] args) {
		String number = "20";
		Context context = new Context(number);
		
//		Expression expression1 = new MinusExpression();
//		expression1.interpret(context);
//		System.out.println(context.getOutput());
//		
//		Expression expression2 = new PlusExpression();
//		expression2.interpret(context);
//		System.out.println(context.getOutput());
//		
//		Expression expression3 = new PlusExpression();
//		expression3.interpret(context);
//		System.out.println(context.getOutput());
//		
//		Expression expression4 = new PlusExpression();
//		expression4.interpret(context);
//		System.out.println(context.getOutput());
		
		List<Expression> list = new ArrayList<Expression>();
		list.add(new PlusExpression());
		list.add(new PlusExpression());
		list.add(new MinusExpression());
		list.add(new MinusExpression());
		list.add(new MinusExpression());
		list.add(new MinusExpression());
		
		for(Expression ex : list) {
			ex.interpret(context);
			System.out.println(context.getOutput());
		}
	}
}
