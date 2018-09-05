package org.haijun.study.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

//@Component
public class FooMessageListener  implements MessageListener {
    @Override
    public void onMessage(Message message) {
        String messageBody = new String(message.getBody());
        System.out.println(" [x] Received '" + messageBody + "'");
    }
}
