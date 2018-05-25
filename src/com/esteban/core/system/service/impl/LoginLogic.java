package com.esteban.core.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.esteban.core.framework.utils.MD5;
import com.esteban.core.framework.utils.StringUtil;
import com.esteban.core.framework.utils.Utility;
import com.esteban.core.framework.utils.WebUtils;
import com.esteban.core.system.model.Oper;
import com.esteban.core.system.model.OperExample;
import com.esteban.core.system.service.ILoginLogic;
import com.esteban.core.system.service.IOperLogic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CPR269 on 2018/5/9.
 */
@Service("loginLogic")
public class LoginLogic implements ILoginLogic{

    @Resource
    private IOperLogic operLogic;

    @Override
    public Object login(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> result=new HashMap<>();

        String dataStr = request.getParameter("data");
        if(Utility.isNotEmpty(dataStr)) {
            JSONObject dataJson = JSONObject.parseObject(dataStr);
            if (!Utility.validRequestPara(new String[]{"userId", "passwd"}, dataJson)) {
                result.put("code", "500");
                result.put("msg", "data中缺少必要参数");
                return result;
            }

            String userId = dataJson.getString("userId");
            String passwd = dataJson.getString("passwd");
            String checkCode = dataJson.getString("checkCode");
            String deviceCode = dataJson.getString("deviceCode");

            String message="";
            //数据验证
            if (StringUtil.isBlank(userId)) {
                message="登录名不能为空";
            }
            if (StringUtil.isBlank(passwd)) {
                message="密码不能为空";
            }
            if("1".equals(WebUtils.getConfigValByName("isValidateCode"))){
                if (StringUtil.isBlank(checkCode)) {
                    message="验证码不能为空";
                }
                if (checkCode!=null&&!checkCode.equals((String)request.getSession(true).getAttribute("adminRand"))) {
                    message="验证码错误";
                }
            }
            if(!StringUtil.isBlank(message)){
                result.put("message", message);
                result.put("status","500");
                result.put("userId", userId);
                return result;
            }

            String validateString = MD5.stringMD5(passwd);
            OperExample operEmp=new OperExample();
            operEmp.or().andNameEqualTo(userId);
            List<Oper> list=operLogic.detail(operEmp);
            Oper oper = null;
            if(list!=null&&list.size()>0){
                oper=list.get(0);
            }
            if (oper != null) {
                Map<String, String> rtn =operLogic.login(oper,validateString,deviceCode,request,response);
                if("200".equals(rtn.get("status"))){
                    result.put("message", rtn.get("message"));
                    result.put("status","200");
                    result.put("token", rtn.get("token"));
                }else{
                    result.put("message", rtn.get("message"));
                    result.put("status","200");
                }
            } else {
                result.put("message", "用户不存在");
                result.put("status","500");
            }
        }
        return result;
    }
}
