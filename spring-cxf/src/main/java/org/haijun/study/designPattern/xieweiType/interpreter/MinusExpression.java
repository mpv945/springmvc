package org.haijun.study.designPattern.xieweiType.interpreter;

public class MinusExpression extends Expression {

	public void interpret(Context context) {
		System.out.println("自动递减");
		String input = context.getInput();
		int inInput = Integer.parseInt(input);
//		inInput--;
		--inInput;
		context.setInput(String.valueOf(inInput));
		context.setOutput(inInput);
	}

}
