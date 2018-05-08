package com.esteban.service.admin.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.esteban.dao.AdviceUserDao;
import com.esteban.dao.OperDao;
import com.esteban.dao.UserLogDao;
import com.esteban.framework.utils.DateOperator;
import com.esteban.framework.utils.IPUtils;
import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.UUID;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.AdviceUser;
import com.esteban.model.Oper;
import com.esteban.model.OperExample;
import com.esteban.model.Role;
import com.esteban.model.RoleExample;
import com.esteban.model.UserLog;
import com.esteban.service.admin.IOperLogic;
import com.esteban.service.admin.IRoleLogic;

@Service
public class OperLogic implements IOperLogic {
	private static final Logger log = Logger.getLogger(OperLogic.class);

	@Resource
	private OperDao operDao;
	
	@Resource
	private UserLogDao userLogDao;
	
	@Resource
	private IRoleLogic roleLogic;
	
	@Resource
	private AdviceUserDao adviceUserDao;
	
	
	public List<Oper> listByPage(Oper t, Page page) {
		return operDao.listByPage(t, page);
	}

	
	public List<Oper> detail(OperExample emp) {
		if(emp!=null){
			return operDao.selectByExample(emp);
		}
		return null;
	}
	
	
	public List<Oper> detailWithBlob(OperExample emp) {
		if(emp!=null){
			return operDao.selectByExampleWithBLOBs(emp);
		}
		return null;
	}
	
	public Oper detailFirst(OperExample emp){
		Oper op=null;
		List<Oper> listOper=detail(emp);
		if(listOper!=null&&listOper.size()>0){
			op=listOper.get(0);
		}
		return op;
	}
	
	
	public Oper detailFirstWithBlob(OperExample emp){
		Oper op=null;
		List<Oper> listOper=detailWithBlob(emp);
		if(listOper!=null&&listOper.size()>0){
			op=listOper.get(0);
		}
		return op;
	}

	
	public boolean add(Oper t) {
		int result=0;
		if(t!=null){
			result=operDao.insert(t);
		}
		return result>0;
	}

	
	public boolean modifyAll(Oper t,OperExample emp) {
		int result=0;
		if(t!=null){
			result=operDao.updateByExample(t,emp);
		}
		return result>0;
	}
	
	public boolean modify(Oper t,OperExample emp) {
		int result=0;
		if(t!=null){
			result=operDao.updateByExampleSelective(t,emp);
		}
		return result>0;
	}

	
	public boolean delete(OperExample emp) {
		int result=0;
		if(emp!=null){
			result=operDao.deleteByExample(emp);
		}
		return result>0;
	}
	
	
    public boolean saveLog(String operUser, String info, String remoteAddr) {
        int result=0;
        if(!StringUtils.isBlank(operUser)){
        	String logid=UUID.getUUID().toString(); 
        	UserLog userLog=new UserLog();
        	userLog.setUserlogid(logid);
        	userLog.setContent(info);
        	userLog.setUserid(operUser);
        	userLog.setIpaddr(remoteAddr);
            result=userLogDao.insert(userLog);
        }
        return result>0;
    }
	
	
    public boolean updateUserLoginInfo(String userid, String remoteAddr, String type, String loginTime, String provName, String areaName) {
        String logid=UUID.getUUID().toString();
        AdviceUser adviceUser=new AdviceUser();
        adviceUser.setId(logid);
        adviceUser.setAreaname(areaName);
        adviceUser.setIpadr(remoteAddr);
        adviceUser.setProvname(provName);
        adviceUser.setTime(loginTime);
        adviceUser.setType(type);
        adviceUser.setUserid(userid);
        return adviceUserDao.insert(adviceUser)>0;
    }

	
	public String login(Oper oper, String validateString,
			HttpServletRequest req, HttpServletResponse res) {
		 	String flag="";
	        if ("1".equals(oper.getStatus())) {
	            if (oper.getPasswd().equals(validateString)) {
	                oper.setPasswd("");

	                // 增加登录日志
	                log.info("用户[" + oper.getName() + "]�?" + DateOperator.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "登陆,Ip:[" + req.getRemoteAddr() + "] ");
	                boolean Logflag=saveLog(oper.getName(), "登录成功[" + oper.getName() + "]", req.getRemoteAddr());
	                String loginTime = DateOperator.format(new Date(), "yyyyMMddHHmmss");
	                String[] loginAddr = IPUtils.getAreaAndAreaSubByIPInSina(req.getRemoteAddr());

	                //修改登录记录
	                boolean Infoflag=updateUserLoginInfo(oper.getName(), req.getRemoteAddr(), "WEB", loginTime, loginAddr[0], loginAddr[1]);
	                
	                if(Logflag&&Infoflag){
		                //登录验证完成，添加session信息并跳�?
		                req.getSession().setAttribute(WebUtils.ADMIN_OPER, oper);
		                req.getSession().setAttribute("currentLoginTime", loginTime);
		                int time=1800;
		                try{
		                	time=Integer.parseInt(WebUtils.getConfigValByName("MaxInactiveInterval"));
		                }catch (Exception e) {
		                	time=1800;
						}
		                req.getSession().setMaxInactiveInterval(time);
		                String path = (String) req.getSession().getAttribute("RedirectPath");
	
		                if (StringUtils.isNotBlank(path)) {
		                    flag = "redirectURL";
		                } else {
		                    flag = "redirectIndex";
		                }
	                }
	            } else {
	                flag = "infoErro";
	            }
	        } else if ("0".equals(oper.getStatus())) {
	            flag="userNotPass";
	        } else {
	            flag="userNotActive";
	        }
	        
	        return flag;
	}

	
	public List<String> getOperRights(Oper oper) {
		OperExample operEmp=new OperExample();
		operEmp.or().andIdEqualTo(oper.getId());
		Oper op=detailWithBlob(operEmp).get(0);
		List<String> opRights= op.getListRights();
		
		RoleExample roleEmp=new RoleExample();
		roleEmp.or().andIdEqualTo(op.getRole());
		Role role=roleLogic.detail(roleEmp).get(0);
		
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
	
}
