package com.esteban.core.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.esteban.core.framework.utils.Base64Utils;
import com.esteban.core.framework.utils.StringUtil;
import com.esteban.core.framework.utils.Utility;
import com.esteban.core.framework.utils.WebUtils;
import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.LoginLog;
import com.esteban.core.system.model.Oper;
import com.esteban.core.system.model.OperExample;
import com.esteban.core.system.service.IOperLogic;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import com.esteban.core.system.dao.MenuTreeDao;
import com.esteban.core.system.model.MenuTree;
import com.esteban.core.system.model.MenuTreeExample;
import com.esteban.core.system.service.IMenuTreeLogic;

@Service
public class MenuTreeLogic extends BaseServiceImpl<MenuTree,MenuTreeExample> implements IMenuTreeLogic {

	@Resource
	private MenuTreeDao menuTreeDao;

    @Resource
    private IOperLogic operLogic;

	@Override
	public IDao getDao() {
		return menuTreeDao;
	}

	public List<MenuTree> queryTreeMenu(String parentNode, List<String> rights) {
		return menuTreeDao.queryMenuTree(parentNode,rights);
	}

    @Override
    public Object getSubMenuByParentId(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> result=new HashMap<>();

        String dataStr = request.getParameter("data");
        String token = request.getParameter("token");
        dataStr = Base64Utils.base64Decode(dataStr);

        if(Utility.isNotEmpty(dataStr)) {
            JSONObject dataJson = JSONObject.parseObject(dataStr);
            if (!Utility.validRequestPara(new String[]{"parentId","grade"}, dataJson)) {
                result.put("code", "500");
                result.put("msg", "data中缺少必要参数");
                return result;
            }

            String parentId = dataJson.getString("parentId");
            String grade = dataJson.getString("grade");

            LoginLog log = WebUtils.checkTokenIsValid(token);
            if (log==null) {
                result.put("code", "403");
                result.put("message", "token无效或用户未登录！");
                return result;
            }

            OperExample operEmp=new OperExample();
            operEmp.createCriteria().andUserCodeEqualTo(log.getUserid());
            List<Oper> list=operLogic.detailWithBlob(operEmp);
            Oper oper = null;
            if(list!=null&&list.size()>0){
                oper=list.get(0);
            }
            if (oper != null) {
                List<String> rights=operLogic.getOperRights(oper);

                List<MenuTree> menus=WebUtils.getMenuByRights(parentId,rights);
                String str="[";
                for(MenuTree m:menus){
                    if("Y".equalsIgnoreCase(m.getIsforder())){
                        str+="['true',";
                    }else{
                        str+="['false',";
                    }
                    str+="'"+m.getName()+"',";
                    if("Y".equalsIgnoreCase(m.getIsforder())){
                        str+="'javascript:;',";
                    }else{
                        str+="'"+(StringUtil.isBlank(m.getUrl())?"javascript:;":m.getUrl())+"',";
                    }
                    str+="'"+m.getId()+"','"+(Integer.parseInt(grade)+1)+"'],";
                }
                if(str.endsWith(",")){
                    str=str.substring(0,str.length()-1);
                }
                str+="]";

                result.put("message","菜单列表获取成功");
                result.put("menuList",str);
                result.put("status","200");
            } else {
                result.put("message", "用户不存在");
                result.put("status","500");
            }

        }
        return result;
    }

    @Override
    public List<Map<String, String>> getDistinctParentId(boolean isShowAllData) {
        return menuTreeDao.getDistinctParentId(isShowAllData);
    }

}
