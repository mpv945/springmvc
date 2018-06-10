package org.haijun.study.cxf;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CxfClient {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"cxfclient/client-beans.xml"});
        Baeldung baeldung = (Baeldung)context.getBean("baeldungTest");
        System.out.println( baeldung.getusers());
    }
}
