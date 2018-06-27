package com.esteban.core.system.controller;

import com.esteban.core.framework.utils.DateOperator;
import com.esteban.core.framework.utils.MD5;
import com.esteban.core.framework.utils.RedisUtils;
import com.esteban.core.framework.utils.SpringBeanFactory;
import com.esteban.core.framework.utils.StringUtil;
import com.esteban.core.framework.utils.Utility;
import com.esteban.core.system.model.InterfaceAdapter;
import com.esteban.core.system.model.InterfaceAdapterExample;
import com.esteban.core.system.service.IInterfaceAdapterLogic;
import com.esteban.core.system.service.base.activeMQ.MessageMQConsumer;
import com.esteban.core.system.service.base.activeMQ.MessageMQReceiver;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;
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
    private Destination messageDestination;
    @Resource
    private MessageMQReceiver mqReceiver;
    @Resource
    private MessageMQConsumer mqConsumer;
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
    public Object handleInterface(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> result=new HashMap<String,Object>();

        String data= request.getParameter("data");
        String adapterNo= request.getParameter("adapterNo");
        String time= request.getParameter("time");
        String ticket= request.getParameter("ticket");
        String token= request.getParameter("token");

//        mqReceiver.sendMessage(messageDestination,"我是一个消息！北京时间为："+ DateOperator.getNowDate());
        TextMessage tm = mqConsumer.receive(messageDestination);

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
