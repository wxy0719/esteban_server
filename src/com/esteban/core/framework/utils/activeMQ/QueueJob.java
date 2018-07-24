package com.esteban.core.framework.utils.activeMQ;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;

/**
 * Created by CPR269 on 2018/7/14.
 */
public class QueueJob {

    private Object queueContent;
    private int sessionMode;
    private Message message;
    private MessageConsumer consumer;

    public QueueJob() {
    }

    public QueueJob(MessageConsumer consumer, Message message, Object queueContent, int sessionMode) {
        this.consumer = consumer;
        this.message = message;
        this.queueContent = queueContent;
        this.sessionMode = sessionMode;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Object getQueueContent() {
        return queueContent;
    }

    public void setQueueContent(Object queueContent) {
        this.queueContent = queueContent;
    }

    public int getSessionMode() {
        return sessionMode;
    }

    public void setSessionMode(int sessionMode) {
        this.sessionMode = sessionMode;
    }

    public MessageConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(MessageConsumer consumer) {
        this.consumer = consumer;
    }

    public void acknowledge(){
        try {
            this.message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            this.consumer.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
