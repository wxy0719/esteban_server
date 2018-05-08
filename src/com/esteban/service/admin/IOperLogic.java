package com.esteban.service.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esteban.model.Oper;
import com.esteban.model.OperExample;
import com.esteban.service.base.BaseService;

public interface IOperLogic extends BaseService<Oper, OperExample>{

	String login(Oper oper, String validateString, HttpServletRequest req,
			HttpServletResponse res);

	boolean saveLog(String operUser, String info, String remoteAddr);

	boolean updateUserLoginInfo(String mobile, String remoteAddr, String type,String loginTime, String provName, String areaName);
	
	List<String> getOperRights(Oper oper);

	String getOperRightsString(Oper oper); 
}
