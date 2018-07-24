package com.esteban.core.framework.utils.activeMQ;

import com.esteban.core.framework.utils.StringUtil;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.ConnectionViewMBean;
import org.apache.activemq.broker.jmx.DurableSubscriptionViewMBean;
import org.apache.activemq.broker.jmx.JobSchedulerViewMBean;
import org.apache.activemq.broker.jmx.NetworkBridgeViewMBean;
import org.apache.activemq.broker.jmx.NetworkConnectorViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.activemq.broker.jmx.SubscriptionViewMBean;
import org.apache.activemq.broker.jmx.TopicViewMBean;
import org.springframework.stereotype.Service;

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
 * Created by CPR269 on 2018/7/14.
 */
@Service
public class MqStatusUtils {

    private BrokerViewMBean brokerViewMBean;
    private MBeanServerConnection connection;

    private void init(String JXM_URL,String brokerName,String type){
        try{
            JMXServiceURL url = new JMXServiceURL(JXM_URL);
            JMXConnector connector = JMXConnectorFactory.connect(url);
            connector.connect();
            connection = connector.getMBeanServerConnection();
            ObjectName name = new ObjectName("org.apache.activemq:brokerName="+brokerName+",type="+type);
            brokerViewMBean = MBeanServerInvocationHandler.newProxyInstance(connection, name, BrokerViewMBean.class, true);
        }catch (IOException e){
            e.printStackTrace();
        }catch (MalformedObjectNameException e){
            e.printStackTrace();
        }
    }

    public BrokerViewMBean getBrokerInfo(String JXM_URL,String brokerName) {
        init(JXM_URL,brokerName,"Broker");
        if(brokerViewMBean!=null){
            return brokerViewMBean;
        }
        return null;
    }

    public QueueViewMBean getQueueInfo(String JXM_URL,String brokerName,String ObjName){
        QueueViewMBean info = null;
        Map<String,QueueViewMBean> queueList=getAllQueueList(JXM_URL,brokerName);
        if(queueList!=null&&queueList.size()>0){
            if(!StringUtil.isBlank(ObjName)){
                info = queueList.get(ObjName);
            }
        }
        return info;
    }

    public Map<String,QueueViewMBean> getAllQueueList(String JXM_URL,String brokerName) {
        Map<String,QueueViewMBean> queueList = new HashMap<>();
        init(JXM_URL,brokerName,"Broker");
        if(brokerViewMBean!=null){
            for (ObjectName qName : brokerViewMBean.getQueues()) {
                QueueViewMBean queueMBean = MBeanServerInvocationHandler.newProxyInstance(connection, qName, QueueViewMBean.class, true);
                if(queueMBean!=null){
                    queueList.put(queueMBean.getName(),queueMBean);
                }
            }
        }
        return queueList;
    }

    public TopicViewMBean getTopicInfo(String JXM_URL,String brokerName,String ObjName){
        TopicViewMBean info = null;
        Map<String,TopicViewMBean> topicList=getAllTopicList(JXM_URL,brokerName);
        if(topicList!=null&&topicList.size()>0){
            if(!StringUtil.isBlank(ObjName)){
                info = topicList.get(ObjName);
            }
        }
        return info;
    }

    public Map<String,TopicViewMBean> getAllTopicList(String JXM_URL,String brokerName) {
        Map<String,TopicViewMBean> topicList = new HashMap<>();
        init(JXM_URL,brokerName,"Broker");
        if(brokerViewMBean!=null){
            for (ObjectName qName : brokerViewMBean.getTopics()) {
                TopicViewMBean topicMBean = MBeanServerInvocationHandler.newProxyInstance(connection, qName, TopicViewMBean.class, true);
                if(topicMBean!=null){
                    topicList.put(topicMBean.getName(),topicMBean);
                }
            }
        }
        return topicList;
    }

    public Map<String,DurableSubscriptionViewMBean> getAllDurableSubscriberList(String JXM_URL,String brokerName) {
        Map<String,DurableSubscriptionViewMBean> subscriberList = new HashMap<>();
        init(JXM_URL,brokerName,"Broker");
        if(brokerViewMBean!=null){
            for (ObjectName qName : brokerViewMBean.getTopics()) {
                DurableSubscriptionViewMBean subscriberMBean = MBeanServerInvocationHandler.newProxyInstance(connection, qName, DurableSubscriptionViewMBean.class, true);
                if(subscriberMBean!=null){
                    subscriberList.put(subscriberMBean.getSubscriptionName(),subscriberMBean);
                }
            }
        }
        return subscriberList;
    }

    public Map<String,SubscriptionViewMBean> getAllSubscriberList(String JXM_URL,String brokerName) {
        Map<String,SubscriptionViewMBean> subscriberList = new HashMap<>();
        init(JXM_URL,brokerName,"Broker");
        if(brokerViewMBean!=null){
            for (ObjectName qName : brokerViewMBean.getTopics()) {
                SubscriptionViewMBean subscriberMBean = MBeanServerInvocationHandler.newProxyInstance(connection, qName, SubscriptionViewMBean.class, true);
                if(subscriberMBean!=null){
                    subscriberList.put(subscriberMBean.getSubscriptionName(),subscriberMBean);
                }
            }
        }
        return subscriberList;
    }

    public Map<String,ConnectionViewMBean> getAllConnectionList(String JXM_URL,String brokerName) {
        Map<String,ConnectionViewMBean> connectionList = new HashMap<>();
        init(JXM_URL,brokerName,"Broker");
        if(brokerViewMBean!=null){
            for (ObjectName qName : brokerViewMBean.getTopics()) {
                ConnectionViewMBean connectionMBean = MBeanServerInvocationHandler.newProxyInstance(connection, qName, ConnectionViewMBean.class, true);
                if(connectionMBean!=null){
                    connectionList.put(connectionMBean.getRemoteAddress(),connectionMBean);
                }
            }
        }
        return connectionList;
    }

    public Map<String,NetworkConnectorViewMBean> getAllNetworkConnectorList(String JXM_URL,String brokerName) {
        Map<String,NetworkConnectorViewMBean> networkConnectorList = new HashMap<>();
        init(JXM_URL,brokerName,"Broker");
        if(brokerViewMBean!=null){
            for (ObjectName qName : brokerViewMBean.getTopics()) {
                NetworkConnectorViewMBean networkConnectorMBean = MBeanServerInvocationHandler.newProxyInstance(connection, qName, NetworkConnectorViewMBean.class, true);
                if(networkConnectorMBean!=null){
                    networkConnectorList.put(networkConnectorMBean.getName(),networkConnectorMBean);
                }
            }
        }
        return networkConnectorList;
    }

    public Map<String,NetworkBridgeViewMBean> getAllNetworkBridgeList(String JXM_URL, String brokerName) {
        Map<String,NetworkBridgeViewMBean> networkBridgeList = new HashMap<>();
        init(JXM_URL,brokerName,"Broker");
        if(brokerViewMBean!=null){
            for (ObjectName qName : brokerViewMBean.getTopics()) {
                NetworkBridgeViewMBean networkBridgeMBean = MBeanServerInvocationHandler.newProxyInstance(connection, qName, NetworkBridgeViewMBean.class, true);
                if(networkBridgeMBean!=null){
                    networkBridgeList.put(networkBridgeMBean.getRemoteBrokerId(),networkBridgeMBean);
                }
            }
        }
        return networkBridgeList;
    }

    public List<JobSchedulerViewMBean> getAllJobSchedulerList(String JXM_URL, String brokerName) {
        List<JobSchedulerViewMBean> jobSchedulerList = new ArrayList<>();
        init(JXM_URL,brokerName,"Broker");
        if(brokerViewMBean!=null){
            for (ObjectName qName : brokerViewMBean.getTopics()) {
                JobSchedulerViewMBean jobSchedulerMBean = MBeanServerInvocationHandler.newProxyInstance(connection, qName, JobSchedulerViewMBean.class, true);
                if(jobSchedulerMBean!=null){
                    jobSchedulerList.add(jobSchedulerMBean);
                }
            }
        }
        return jobSchedulerList;
    }
}
