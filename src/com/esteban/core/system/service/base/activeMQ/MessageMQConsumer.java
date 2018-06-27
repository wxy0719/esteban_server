package com.esteban.core.system.service.base.activeMQ;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Created by CPR269 on 2018/6/27.
 */
@Service
public class MessageMQConsumer {

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    public TextMessage receive(Destination destination) {
        TextMessage tm = (TextMessage) jmsTemplate.receive(destination);
        if (tm != null) {
            try {
                System.out.println("Get Message: " + tm.getText() + " from Destination " + destination.toString());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        return tm;
    }

}