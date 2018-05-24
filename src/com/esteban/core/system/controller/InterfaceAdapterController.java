package com.esteban.core.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.esteban.core.framework.utils.*;
import com.esteban.core.system.model.InterfaceAdapter;
import com.esteban.core.system.model.InterfaceAdapterExample;
import com.esteban.core.system.service.IInterfaceAdapterLogic;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private IInterfaceAdapterLogic interfaceAdapterLogic;

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
        if(Utility.isEmpty(token)){
            result.put("code","401");
            result.put("message","token不能为空！");
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
                result.put("result",rtn);
                return result;

            } catch (Exception e) {
                e.printStackTrace();
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

    public static void main(String[] args) {
        String millis=String.valueOf(System.currentTimeMillis()/1000)+ Utility.getRandomNum(8);
        //MD5加密
        JSONObject data = new JSONObject();

        //插入data   100068
        data.put("entityCode","HD340400");
        data.put("uid","Admin");
        data.put("id", "");
        data.put("brandName", "BMW");
        data.put("seriesName", "BMW2017");
        data.put("plateNumber", "浙Q78647");
        data.put("pushShopID", "HD340400");
        data.put("longitude", "102.12318");
        data.put("latitude", "256.26541");
        data.put("address", "龙阳大道邱家大湾");
        data.put("photo", "http://123.45.123.23/src/1.jpg");
        data.put("name", "张三");
        data.put("phone", "13856295489");

        String ticket = MD5.stringMD5(millis+data.toJSONString());

        Map<String,String> params = new HashMap<>();
        params.put("adapterNo", "10001");
        params.put("ticket", ticket);
        params.put("data", data.toJSONString());
        params.put("time", millis);
        params.put("token", "token");

        JSONObject obj = HttpClientUtil.postForm("http://localhost:8082/esteban/interfaceAdapter", params, true);
        System.out.println(obj);

    }
}
