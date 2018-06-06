package org.haijun.study.cxf;


import org.springframework.stereotype.Service;

import javax.jws.WebService;

@Service("helloWorld")
@WebService
public class HelloWorldService {

	public String sayHello() {
		return "test123";
	}
}
