package com.esteban.core.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.esteban.core.framework.utils.Base64Utils;
import com.esteban.core.framework.utils.Page;
import com.esteban.core.framework.utils.StringUtil;
import com.esteban.core.framework.utils.Utility;
import com.esteban.core.framework.utils.WebUtils;
import com.esteban.core.system.dao.RightsDao;
import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.LoginLog;
import com.esteban.core.system.model.Rights;
import com.esteban.core.system.model.RightsExample;
import com.esteban.core.system.service.IRightsLogic;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RightsLogic extends BaseServiceImpl<Rights,RightsExample> implements IRightsLogic {

	@Resource
	private RightsDao rightsDao;


	@Override
	public IDao getDao() {
		return rightsDao;
	}

	public List<Map<String, Object>> listAllInfo(Rights rights, Page webPage) {
		return rightsDao.listAllInfo(rights,webPage);
	}

    public Object getRights(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> result = new HashMap<>();

        String dataStr = request.getParameter("data");
        dataStr = Base64Utils.base64Decode(dataStr);

        if(Utility.isNotEmpty(dataStr)) {
            JSONObject dataJson = JSONObject.parseObject(dataStr);
            if (!Utility.validRequestPara(new String[]{"token"}, dataJson)) {
                result.put("code", "500");
                result.put("msg", "data中缺少必要参数");
                return result;
            }

            String token = dataJson.getString("token");
            LoginLog log = WebUtils.checkTokenIsValid(token);
            if(log==null){
                result.put("code","403");
                result.put("message","token无效或用户未登录！");
                return result;
            }

            String id = dataJson.getString("id");
            String status = dataJson.getString("status");
            String type = dataJson.getString("type");

            RightsExample rightsExm = new RightsExample();
            if(!StringUtil.isBlank(id)){
                rightsExm.or().andIdEqualTo(id);
            }else if(!StringUtil.isBlank(status)){
                rightsExm.or().andStatusEqualTo(status);
            }if(!StringUtil.isBlank(type)){
                rightsExm.or().andTypeEqualTo(type);
            }

            List<Rights> listRights = detail(rightsExm);

            result.put("message","查询成功！");
            result.put("status","200");
            result.put("result",listRights);
        }

        return result;
    }

}
