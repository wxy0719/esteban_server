package com.esteban.core.framework.utils.activeMQ;

import com.alibaba.fastjson.JSONObject;
import com.esteban.core.framework.utils.StringUtil;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.springframework.stereotype.Service;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CPR269 on 2018/7/13.
 */
@Service
public class MqP2PUtils {

    private Connection connection;
    //一个操作会话
    private Session session;
    //目的地，其实就是连接到哪个队列，如果是点对点，那么它的实现是Queue，如果是订阅模式，那它的实现是Topic
    private Destination destination;
    //生产者，就是产生数据的对象
    private MessageProducer producer;
    //消费者，就是接收数据的对象
    private MessageConsumer consumer;

    private void getProducerConfig(String queueName,int SESSION_MODE,int DELIVERY_MODE){
        try {
            //从工厂中获取一个连接
            connection = MqConnectionFactory.getConnection();
            //创建一个session
            //第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
            //第二个参数为false时，paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
            //Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
            //Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
            //DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
            session = connection.createSession(false, SESSION_MODE);
            //创建一个到达的目的地，其实想一下就知道了，activemq不可能同时只能跑一个队列吧，这个会话将会到这个队列，当然，如果这个队列不存在，将会被创建
            destination = session.createQueue(queueName);
            //从session中，获取一个消息生产者
            producer = session.createProducer(destination);
            //设置生产者的模式，有两种可选
            //DeliveryMode.PERSISTENT 当activemq关闭的时候，队列数据将会被保存
            //DeliveryMode.NON_PERSISTENT 当activemq关闭的时候，队列里面的数据将会被清空
            producer.setDeliveryMode(DELIVERY_MODE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void getConsumerConfig(String queueName,int SESSION_MODE){
        try {
            //从工厂中获取一个连接
            connection = MqConnectionFactory.getConnection();
            //创建一个session
            //第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
            //第二个参数为false时，paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
            //Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
            //Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
            //DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
            session = connection.createSession(false, SESSION_MODE);
            //创建一个到达的目的地，其实想一下就知道了，activemq不可能同时只能跑一个队列吧，这个会话将会到这个队列，当然，如果这个队列不存在，将会被创建
            destination = session.createQueue(queueName);
            //根据session，创建一个接收者对象
            consumer = session.createConsumer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendTextMsg(String text,String queueName,Long liveTime,int SESSION_MODE,int DELIVERY_MODE) throws JMSException {
        getProducerConfig(queueName,SESSION_MODE,DELIVERY_MODE);

        TextMessage textMsg = null;
        try {
            textMsg = session.createTextMessage(text);
            if(liveTime!=null){
                producer.setTimeToLive(liveTime);
            }
            producer.send(textMsg);

            System.out.println("【"+queueName+"】队列发送消息成功!   "+text);
            //即便生产者的对象关闭了，程序还在运行哦
            producer.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            connection.close();
        }
    }

    public void sendJsonMsg(JSONObject jsonObj, String queueName,Long liveTime,int SESSION_MODE,int DELIVERY_MODE) throws JMSException {
        getProducerConfig(queueName,SESSION_MODE,DELIVERY_MODE);

        ObjectMessage objMsg = null;
        try {
            objMsg = session.createObjectMessage(jsonObj);
            if(liveTime!=null){
                producer.setTimeToLive(liveTime);
            }
            producer.send(objMsg);

            System.out.println("【"+queueName+"】队列发送消息成功!");
            //即便生产者的对象关闭了，程序还在运行哦
            producer.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            connection.close();
        }
    }

    public QueueJob getTextMsg(String queueName,int SESSION_MODE) throws JMSException {
        getConsumerConfig(queueName,SESSION_MODE);
        QueueJob job = new QueueJob();
        try {
            TextMessage textMessage = (TextMessage)consumer.receive(100000);
            job.setQueueContent(textMessage.getText());
            job.setMessage(textMessage);
            job.setSessionMode(SESSION_MODE);
            job.setConsumer(consumer);

            return job;
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            connection.close();
        }
        return null;
    }

    public QueueJob getJsonMsg(String queueName,int SESSION_MODE) throws JMSException {
        getConsumerConfig(queueName,SESSION_MODE);
        QueueJob job = new QueueJob();
        try {
            ObjectMessage objectMessage = (ObjectMessage)consumer.receive();
            job.setQueueContent(objectMessage.getObject());
            job.setMessage(objectMessage);
            job.setSessionMode(SESSION_MODE);
            job.setConsumer(consumer);

            return job;
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            connection.close();
        }
        return null;
    }

    public List<String> getAllTextMsg(String queueName,int SESSION_MODE) throws JMSException {
        final List<String> list = new ArrayList<>();
        getConsumerConfig(queueName,SESSION_MODE);

        //实现一个消息的监听器
        //实现这个监听器后，以后只要有消息，就会通过这个监听器接收到
        try {
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        //获取到接收的数据
                        String t = ((TextMessage)message).getText();
                        list.add(t);
                        message.acknowledge();
                        System.out.println(t);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            //关闭接收端，也不会终止程序哦
//            consumer.close();

            return list;
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            connection.close();
        }
        return list;
    }

    public List<Object> getAllJsonMsg(String queueName,int SESSION_MODE) throws JMSException {
        final List<Object> list = new ArrayList<>();
        getConsumerConfig(queueName,SESSION_MODE);

        //实现一个消息的监听器
        //实现这个监听器后，以后只要有消息，就会通过这个监听器接收到
        try {
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        //获取到接收的数据
                        Object o = ((ObjectMessage)message).getObject();
                        list.add(o);
                        message.acknowledge();
                        System.out.println(o);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            //关闭接收端，也不会终止程序哦
            consumer.close();

            return list;
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            connection.close();
        }
        return list;
    }

}
