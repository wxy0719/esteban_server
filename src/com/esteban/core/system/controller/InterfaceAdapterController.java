package com.esteban.core.system.controller;

import com.esteban.core.framework.utils.DateOperator;
import com.esteban.core.framework.utils.MD5;
import com.esteban.core.framework.utils.SpringBeanFactory;
import com.esteban.core.framework.utils.StringUtil;
import com.esteban.core.framework.utils.Utility;
import com.esteban.core.framework.utils.activeMQ.MqP2PUtils;
import com.esteban.core.framework.utils.activeMQ.MqRSSUtils;
import com.esteban.core.framework.utils.activeMQ.MqStatusUtils;
import com.esteban.core.framework.utils.activeMQ.QueueJob;
import com.esteban.core.framework.utils.redis.RedisUtils;
import com.esteban.core.system.model.InterfaceAdapter;
import com.esteban.core.system.model.InterfaceAdapterExample;
import com.esteban.core.system.service.IInterfaceAdapterLogic;
import com.esteban.core.system.service.base.activeMQ.DefaultMessageMQReceiver;
import com.esteban.core.system.service.base.activeMQ.DefaultMessageMQSender;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.activemq.broker.jmx.TopicViewMBean;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CPR269 on 2018/5/22.
 */
@Controller
@RequestMapping("")
public class InterfaceAdapterController {

    private static final Logger log = Logger.getLogger(InterfaceAdapterController.class);

    @Resource
    private DefaultMessageMQSender mqSender;
    @Resource
    private DefaultMessageMQReceiver mqReceiver;
    @Resource
    private MqP2PUtils mqP2PUtils;
    @Resource
    private MqRSSUtils mqRSSUtils;
    @Resource
    private MqStatusUtils mqStatusUtils;
    @Resource
    private DefaultMessageMQSender defaultMessageMQSender;
    @Resource
    private IInterfaceAdapterLogic interfaceAdapterLogic;
    @Resource
    RedisUtils redisUtils;

    /**
     * 判断数据是否符合格式，并包含必要的数据 【data，adapterNo，time，validator，token】
     *
     * 返回格式【code,message】
     * code: 200,成功; 400,程序错误; 401,参数错误; 403,权限错误; 500,安全错误;
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/interfaceAdapter")
    @ResponseBody
    public Object handleInterface(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> result=new HashMap<String,Object>();

        String data= request.getParameter("data");
        String adapterNo= request.getParameter("adapterNo");
        String time= request.getParameter("time");
        String ticket= request.getParameter("ticket");
        String token= request.getParameter("token");

        mqP2PUtils.sendTextMsg("我是一个消息！北京时间为："+ DateOperator.getNowDate(),"text-queue",null, Session.CLIENT_ACKNOWLEDGE, DeliveryMode.PERSISTENT);
        QueueJob job = mqP2PUtils.getTextMsg("text-queue", Session.CLIENT_ACKNOWLEDGE);
        job.acknowledge();
        job.close();
        System.out.println(job.getQueueContent());

        mqRSSUtils.sendTextMsg("i am a text message,now time:"+ DateOperator.getNowDate(),"english-queue",null, DeliveryMode.PERSISTENT);

        System.out.println(redisUtils.getString("0"));

//        Destination queueDestination = new ActiveMQQueue("text-queue");
//        defaultMessageMQSender.sendMessage(queueDestination,"我是一个消息！北京时间为："+ DateOperator.getNowDate());
//
//        Destination topicDestination = new ActiveMQTopic("english-queue");
//        defaultMessageMQSender.sendMessage(topicDestination,"i am a text message,now time:"+ DateOperator.getNowDate());

        QueueViewMBean info = mqStatusUtils.getQueueInfo("service:jmx:rmi:///jndi/rmi://127.0.0.1:1089/jmxrmi","activemq-cluster","text-queue");
        System.out.println("队列名称: 【"+info.getName()+"】，队列剩余消息数量:"+info.getQueueSize()+"，队列消费者数量:"+info.getConsumerCount()+"，队列已出消息数量:"+info.getDequeueCount()+"，队列中总消息数:"+info.getEnqueueCount());

        TopicViewMBean info1 = mqStatusUtils.getTopicInfo("service:jmx:rmi:///jndi/rmi://127.0.0.1:1089/jmxrmi","activemq-cluster","english-queue");
        System.out.println("队列名称: 【"+info1.getName()+"】，队列剩余消息数量:"+info1.getQueueSize()+"，队列消费者数量:"+info1.getConsumerCount()+"，队列已出消息数量:"+info1.getDequeueCount()+"，队列中总消息数:"+info1.getEnqueueCount());


        //校验参数
        if(Utility.isEmpty(data)){
            result.put("code","401");
            result.put("message","参数data不能为空！");
            return result;
        }
        if(Utility.isEmpty(adapterNo)){
            result.put("code","401");
            result.put("message","访问接口编号不能为空！");
            return result;
        }
        if(Utility.isEmpty(time)){
            result.put("code","401");
            result.put("message","时间不能为空！");
            return result;
        }
        if(Utility.isEmpty(ticket)){
            result.put("code","401");
            result.put("message","校验码不能为空！");
            return result;
        }

        //检验参数是否被篡改
        if(checkDataIsValid(time,data,ticket)){
            try {
                //获取接口方法，并执行
                InterfaceAdapterExample adapterExm=new InterfaceAdapterExample();
                adapterExm.or().andAdapterNoEqualTo(adapterNo);
                InterfaceAdapter adapter = interfaceAdapterLogic.detailFirst(adapterExm);
                String serviceName=adapter.getServiceName();
                String serviceMethod=adapter.getServiceMethod();

                if(StringUtil.isBlank(serviceName)){
                    result.put("code","401");
                    result.put("message",adapterNo+"对应的接口名称不存在！");
                    return result;
                }
                if(StringUtil.isBlank(serviceMethod)){
                    result.put("code","401");
                    result.put("message",adapterNo+"对应的接口方法不存在！");
                    return result;
                }

                Object serviceObj = SpringBeanFactory.getBean(serviceName);
                if(serviceObj == null){
                    result.put("code","401");
                    result.put("message",adapterNo+"对应的接口类不存在！");
                    return result;
                }

                Method method = serviceObj.getClass().getMethod(serviceMethod,new Class[]{HttpServletRequest.class,HttpServletResponse.class});
                Object rtn = method.invoke(serviceObj, new Object[]{request,response});

                result.put("code","200");
                result.put("message","接口平台访问成功！");
                result.put("data",rtn);
                return result;

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                result.put("code","401");
                result.put("message",adapterNo+"对应的接口方法不存在！");
                return result;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                result.put("code","401");
                result.put("message",adapterNo+"程序执行错误！");
                return result;
            }
        }else{
            result.put("code","500");
            result.put("message","参数校验不合法，请重试！");
            return result;
        }
        return result;
    }

    private boolean checkDataIsValid(String time,String data,String validator){
        boolean flag=false;

        // 秘钥有效时长不超过5分钟
        Long diff = System.currentTimeMillis() - Long.valueOf(time.substring(0, 10)) * 1000;
        if(diff > 5 * 60 * 1000){
            return flag;
        }

        //校验参数是否被篡改
        String ticket = MD5.stringMD5(time+data);
        if(ticket.equalsIgnoreCase(validator)){
            flag=true;
        }

        return flag;
    }

}
