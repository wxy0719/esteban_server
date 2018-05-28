package com.esteban.core.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.esteban.core.framework.utils.HttpClientUtil;
import com.esteban.core.framework.utils.MD5;
import com.esteban.core.framework.utils.Utility;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CPR269 on 2018/5/28.
 */
public class AdapterTestClass {


    public static void main(String[] args) {
        String millis=String.valueOf(System.currentTimeMillis()/1000)+ Utility.getRandomNum(8);
        //MD5加密
        JSONObject data = new JSONObject();

        //登录 10001
//        data.put("userId", "admin");
//        data.put("passwd", "123456");

        //获取权限 10002
        data.put("token","eyd0eXAnOidKV1QnLCdhbGcnOidIUzI1Nid9.eydpc3MnOidlc3RlYmFuJywnc3ViJzonMScsJ2V4cCc6JzE4MDBzJywnaWF0JzonMTUyNzQ5OTA4NjExNid9.bd06637aea3fbea4ca9b27606f9f0a7b");

        String ticket = MD5.stringMD5(millis+data.toJSONString());

        Map<String,String> params = new HashMap<>();
        params.put("adapterNo", "10002");
        params.put("ticket", ticket);
        params.put("data", data.toJSONString());
        params.put("time", millis);
        params.put("token", "token");

        JSONObject obj = HttpClientUtil.postForm("http://localhost:8080/esteban_server/interfaceAdapter", params, true);
        System.out.println(obj);
    }
}
