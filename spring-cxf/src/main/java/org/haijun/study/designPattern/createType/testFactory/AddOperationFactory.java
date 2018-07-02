package org.haijun.study.designPattern.createType.testFactory;

public class AddOperationFactory implements OperationFactory{

	public Operation getOperation() {
		return new AddOperation();
	}
	
}
