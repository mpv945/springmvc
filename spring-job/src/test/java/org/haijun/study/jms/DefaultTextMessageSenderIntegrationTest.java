package org.haijun.study.jms;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.haijun.study.activemq.SampleJmsMessageSender;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class DefaultTextMessageSenderIntegrationTest {

    private static SampleJmsMessageSender messageProducer;

    @SuppressWarnings("resource")
    @BeforeClass
    public static void setUp() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("WEB-INF/job/job-jms.xml");//, "classpath:applicationContext.xml"
        messageProducer = (SampleJmsMessageSender) applicationContext.getBean("SampleJmsMessageSender");
    }

    @Test
    public void testSimpleSend() {
        messageProducer.simpleSend();
    }


}
