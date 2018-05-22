package com.esteban.core.system.service.impl;

import com.esteban.core.framework.utils.MD5;
import com.esteban.core.framework.utils.StringUtil;
import com.esteban.core.framework.utils.Utility;
import com.esteban.core.framework.utils.WebUtils;
import com.esteban.core.system.model.Oper;
import com.esteban.core.system.model.OperExample;
import com.esteban.core.system.service.ILoginLogic;
import com.esteban.core.system.service.IOperLogic;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CPR269 on 2018/5/9.
 */
public class LoginLogic implements ILoginLogic{

    private IOperLogic operLogic;

    @Override
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> result=new HashMap<>();

        String dataStr = request.getParameter("data");
        if(Utility.isNotEmpty(dataStr)) {
            JSONObject dataJson = JSONObject.fromObject(dataStr);
            if (!Utility.validRequestPara(new String[]{"entityCode", "uid"}, dataJson)) {
                result.put("code", "500");
                result.put("msg", "data中缺少必要参数");
                return result;
            }

            String userId = dataJson.getString("userId");
            String passwd = dataJson.getString("passwd");
            String checkCode = dataJson.getString("checkCode");

            String flag="";
            //数据验证
            if (StringUtil.isBlank(userId)) {
                flag="nameNotNull";
            }
            if (StringUtil.isBlank(passwd)) {
                flag="passNotNull";
            }
            if("1".equals(WebUtils.getConfigValByName("isValidateCode"))){
                if (StringUtil.isBlank(checkCode)) {
                    flag="codeNotNull";
                }
                if (checkCode!=null&&!checkCode.equals((String)request.getSession(true).getAttribute("adminRand"))) {
                    flag="codeErro";
                }
            }
            if(!StringUtil.isBlank(flag)){
                result.put("message", flag);
                result.put("status","500");
                result.put("userId", userId);
                return result;
            }

            String validateString = MD5.MD5Encode(passwd);
            OperExample operEmp=new OperExample();
            operEmp.or().andNameEqualTo(userId);
            List<Oper> list=operLogic.detail(operEmp);
            Oper oper = null;
            if(list!=null&&list.size()>0){
                oper=list.get(0);
            }
            if (oper != null) {
                flag=operLogic.login(oper,validateString,request,response);
            } else {
                result.put("message", "userNotExist");
                result.put("status","500");
                flag="userNotExist";
            }

        }

        return result;
    }
}
