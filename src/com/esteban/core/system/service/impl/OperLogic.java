package com.esteban.core.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.esteban.core.framework.utils.Base64Utils;
import com.esteban.core.framework.utils.DateOperator;
import com.esteban.core.framework.utils.IPUtils;
import com.esteban.core.framework.utils.StringUtil;
import com.esteban.core.framework.utils.TokenUtils;
import com.esteban.core.framework.utils.UUID;
import com.esteban.core.framework.utils.Utility;
import com.esteban.core.framework.utils.WebUtils;
import com.esteban.core.system.dao.OperDao;
import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.LoginLog;
import com.esteban.core.system.model.LoginLogExample;
import com.esteban.core.system.model.MenuTree;
import com.esteban.core.system.model.Oper;
import com.esteban.core.system.model.OperExample;
import com.esteban.core.system.model.OperateLog;
import com.esteban.core.system.model.Role;
import com.esteban.core.system.model.RoleExample;
import com.esteban.core.system.service.ILoginLogLogic;
import com.esteban.core.system.service.IOperLogic;
import com.esteban.core.system.service.IOperateLogLogic;
import com.esteban.core.system.service.IRoleLogic;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OperLogic extends BaseServiceImpl<Oper,OperExample> implements IOperLogic {
	private static final Logger log = Logger.getLogger(OperLogic.class);

	@Resource
	private OperDao operDao;

	@Resource
	private IOperateLogLogic operateLogLogic;
	
	@Resource
	private IRoleLogic roleLogic;
	
	@Resource
	private ILoginLogLogic loginLogLogic;

    @Resource
    private MenuTreeLogic menuTreeLogic;

	@Override
	public IDao getDao() {
		return operDao;
	}

	public Map<String, String> login(Oper oper, String validateString, String deviceCode, String mac , HttpServletRequest req, HttpServletResponse res) {
		Map<String, String> result = new HashMap<>();
		String status = "400";
		String message = "";
		String token = "";

		if ("1001".equals(oper.getStatus())) {
			if (oper.getPasswd().equalsIgnoreCase(validateString)) {
				//生成token
				token = TokenUtils.getToken(oper.getId(),deviceCode);

				// 增加登录日志
				log.info("用户[" + oper.getName() + "]: " + DateOperator.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "登陆,Ip:[" + req.getRemoteAddr() + "] ");
				saveLog(oper.getName(), "登录成功[" + oper.getName() + "]", req.getRemoteAddr());

				//修改登录记录
				String loginTime = DateOperator.getNowDate();
				String[] loginAddr = IPUtils.getAreaAndAreaSubByIPInSina(req.getRemoteAddr());
				updateUserLoginInfo(oper.getUserCode(), req.getRemoteAddr(), mac , "WEB", loginTime, loginAddr[0], loginAddr[1], token);

				//登录验证完成
				status = "200";
				message = "登录成功";
			} else {
				message = "用户名密码验证不通过";
			}
		} else if ("1002".equals(oper.getStatus())) {
			message="用户未启用";
		} else {
			message="用户已失效";
		}

		result.put("status",status);
		result.put("message",message);
		result.put("token",token);
		return result;
	}

    public Map<String, String> refreshLogin(Oper oper, LoginLog loginLog, String mac, HttpServletRequest req, HttpServletResponse res) {
        Map<String, String> result = new HashMap<>();
        String status = "400";
        String message = "";
        String token = loginLog.getToken();

        if ("1001".equals(oper.getStatus())) {
            if (!StringUtil.isBlank(mac)&&mac.equalsIgnoreCase(loginLog.getMac())) {
                //生成token
                //token = TokenUtils.getToken(oper.getId(),"");

                // 增加登录日志
                log.info("用户[" + oper.getName() + "]: " + DateOperator.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "登陆,Ip:[" + req.getRemoteAddr() + "] ");
                saveLog(oper.getName(), "登录成功[" + oper.getName() + "]", req.getRemoteAddr());

                //修改登录记录
                String loginTime = DateOperator.getNowDate();
                String[] loginAddr = IPUtils.getAreaAndAreaSubByIPInSina(req.getRemoteAddr());
                updateUserLoginInfo(oper.getUserCode(), req.getRemoteAddr(), mac , "WEB", loginTime, loginAddr[0], loginAddr[1], token);

                //登录验证完成
                status = "200";
                message = "登录成功";
            } else {
                message = "登录失效，请重新登录";
            }
        } else if ("1002".equals(oper.getStatus())) {
            message="用户未启用";
        } else {
            message="用户已失效";
        }

        result.put("status",status);
        result.put("message",message);
        result.put("token",token);
        return result;
    }

	public void saveLog(String operUser, String info, String remoteAddr) {
		if(!StringUtils.isBlank(operUser)){
			String logid=UUID.getUUID().toString();
			OperateLog operateLog=new OperateLog();
			operateLog.setLogid(logid);
			operateLog.setContent(info);
			operateLog.setUserid(operUser);
			operateLog.setIpaddr(remoteAddr);
			operateLog.setTime(DateOperator.getNowDate());
			operateLogLogic.insert(operateLog);
		}
	}

	public void updateUserLoginInfo(String userid, String remoteAddr, String mac, String type, String loginTime, String provName, String areaName, String token) {
		//判断是否有该用户的登录记录
		LoginLogExample loginExm=new LoginLogExample();
		loginExm.createCriteria().andUseridEqualTo(userid);
		LoginLog l = loginLogLogic.detailFirst(loginExm);
        String expireSecond="1800000";

		if(l==null){
			//如果没有记录，则插入一条记录
			String logid=UUID.getUUID().toString();
			LoginLog loginLog=new LoginLog();
			loginLog.setId(logid);
			loginLog.setAreaname(areaName);
			loginLog.setIpadr(remoteAddr);
            loginLog.setMac(mac);
			loginLog.setProvname(provName);
			loginLog.setTime(loginTime);
			loginLog.setType(type);
			loginLog.setUserid(userid);
			loginLog.setToken(token);
            loginLog.setExpireSecond(expireSecond);
			loginLogLogic.insert(loginLog);
		}else{
			//如果已有记录，则更新这条记录的对应信息
			l.setAreaname(areaName);
			l.setIpadr(remoteAddr);
            l.setMac(mac);
			l.setProvname(provName);
			l.setTime(loginTime);
			l.setType(type);
			l.setToken(token);
            l.setExpireSecond(expireSecond);
			loginExm.clear();
			loginExm.createCriteria().andIdEqualTo(l.getId()).andUseridEqualTo(userid);
			loginLogLogic.update(l,loginExm);
		}
	}

    public Oper getOperById(String userId){
        Oper oper = null;
        OperExample operExm = new OperExample();
        operExm.createCriteria().andIdEqualTo(userId);
        oper = detailFirst(operExm);

        return oper;
    }
	
	public List<String> getOperRights(Oper oper) {
		OperExample operEmp=new OperExample();
		operEmp.or().andIdEqualTo(oper.getId());
		Oper op=detailWithBlob(operEmp).get(0);
		List<String> opRights= op.getListRights();
		
		RoleExample roleEmp=new RoleExample();
		roleEmp.or().andIdEqualTo(op.getRole());
		Role role=roleLogic.detailWithBlob(roleEmp).get(0);
		
		if(role!=null&&!"2".equals(role.getStatus())){
			List<String> roleRights=role.getListRights();
			if(opRights!=null){
				if(roleRights!=null){
					roleRights.removeAll(opRights);
					opRights.addAll(roleRights);
				}
			}else{
				opRights=roleRights;
			}
		}
		return opRights;
	}

	
	public String getOperRightsString(Oper oper) {
		List<String> operRights= getOperRights(oper);
		String operRightString="";
		for(String str:operRights){
			operRightString+=str+",";
		}
		operRightString=operRightString.substring(0, operRightString.length()-1);
		return operRightString;
	}

    /**
     * 主页获取用户信息接口。
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object getOperInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> result=new HashMap<>();

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

            String sevenDayBeforeTime = DateOperator.getOffsetDayDate(-7,"yyyy-MM-dd HH:mm:ss");
            LoginLogExample logEmp = new LoginLogExample();
            logEmp.createCriteria().andTokenEqualTo(token).andTimeGreaterThanOrEqualTo(sevenDayBeforeTime);
            LoginLog log = loginLogLogic.detailFirst(logEmp);
            if (log == null) {
                result.put("message", "登录已失效，请重新登录");
                result.put("status", "403");
            }

            OperExample operEmp=new OperExample();
            operEmp.createCriteria().andUserCodeEqualTo(log.getUserid());
            List<Oper> list=detail(operEmp);
            Oper oper = null;
            if(list!=null&&list.size()>0){
                oper=list.get(0);
            }
            if (oper != null) {
                oper.setPasswd(null);
                List<String> rights=getOperRights(oper);
                oper.setListRights(rights);

                List<MenuTree> menus=WebUtils.getMenuByRights("0",rights);
                String str="[";
                for(MenuTree m:menus){
                    str+="['true','"+m.getName()+"',"+"'"+(StringUtil.isBlank(m.getUrl())?"javascript:;":m.getUrl())+"','"+m.getId()+"','0'],";
                }
                if(str.endsWith(",")){
                    str=str.substring(0,str.length()-1);
                }
                str+="]";

                result.put("message","用户信息获取成功");
                result.put("menuList",str);
                result.put("userInfo",oper);
                result.put("status","200");
            } else {
                result.put("message", "用户不存在");
                result.put("status","500");
            }
        }
        return result;
    }

}
