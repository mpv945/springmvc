package org.haijun.study.rabbit;

public class Consumer {
    public void listen(String foo) {
        System.out.println("消费的消息内容="+foo);
    }
}
