package com.esteban.core.framework.utils.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.EnhancedConnection;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.JMSException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;

/**
 * Created by CPR269 on 2018/7/13.
 */
public class MqConnectionFactory {
    /**
     * 连接
     */
    private static LinkedList<EnhancedConnection> connectionList = new LinkedList<>();

    private MqConnectionFactory() {}

    private static int maxConnectionNum = 20;

    // 初始化连接池等工作
    static{
        //根据用户名，密码，url创建一个连接工厂
        String url = "failover:(tcp://127.0.0.1:51511,tcp://127.0.0.1:51512,tcp://127.0.0.1:51513)?Randomize=false&initialReconnectDelay=1000";
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setUserName("admin");
        activeMQConnectionFactory.setPassword("admin");
        activeMQConnectionFactory.setBrokerURL(url);
        activeMQConnectionFactory.setConnectResponseTimeout(50000);

        try{
            for(int i=0;i<maxConnectionNum;i++) {
                PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(activeMQConnectionFactory);
                // session数
                int maximumActive = 200;
                pooledConnectionFactory.setMaximumActiveSessionPerConnection(maximumActive);
                pooledConnectionFactory.setIdleTimeout(120);
                pooledConnectionFactory.setMaxConnections(5);
                pooledConnectionFactory.setBlockIfSessionPoolIsFull(true);
                //从工厂中获取一个连接
                PooledConnection connection = (PooledConnection) pooledConnectionFactory.createConnection();
                // 必须start，否则无法接收消息
                connection.start();
                //将创建的连接添加的list中
                connectionList.add(connection);
            }
            System.out.println("初始化activeMq连接池，共创建"+maxConnectionNum+"个连接加入连接池");
        }catch(JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    public static void closeAllConnections() {
        try{
            if(connectionList != null) {
                for(EnhancedConnection connection: connectionList){
                    connection.close();
                }
            }
        }catch(JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一个连接
     */
    public static EnhancedConnection getConnection() {
        if(connectionList!=null&&connectionList.size()>0){
            //从集合中获取一个连接
            final EnhancedConnection conn = connectionList.removeFirst();

            //返回Connection的代理对象
            return (EnhancedConnection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), new Class[]{EnhancedConnection.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if(!"close".equals(method.getName())){
                        return method.invoke(conn, args);
                    }else{
                        connectionList.add(conn);
                        return null;
                    }
                }
            });

        }else{
            throw new RuntimeException("activeMq连接池繁忙，稍后再试");
        }
    }

    public static void init(){}

}
