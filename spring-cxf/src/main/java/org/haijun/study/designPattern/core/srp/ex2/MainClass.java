package org.haijun.study.designPattern.core.srp.ex2;

/**
 * 单一职责原则：不要存在多于一个导致类变更的原因，也就是说每个类应该实现单一的职责，如若不然，就应该把类拆分。
 *
 */
public class MainClass {
	public static void main(String[] args) {
		Input input = new Input();
		input.shuru();
		Validator validator = new Validator();
		if(validator.validate(input.getUsername(), input.getUpassword())){
			DBManager.getConnection();
			DAOImp dao = new DAOImp();
			dao.save(input.getUsername(), input.getUpassword());
		}
	}
}
