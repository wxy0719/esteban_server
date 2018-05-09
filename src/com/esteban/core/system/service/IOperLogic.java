package com.esteban.core.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esteban.core.system.model.Oper;
import com.esteban.core.system.model.OperExample;
import com.esteban.core.system.service.base.IBaseService;

public interface IOperLogic extends IBaseService<Oper, OperExample> {

	String login(Oper oper, String validateString, HttpServletRequest req,
			HttpServletResponse res);

	boolean saveLog(String operUser, String info, String remoteAddr);

	boolean updateUserLoginInfo(String mobile, String remoteAddr, String type,String loginTime, String provName, String areaName);
	
	List<String> getOperRights(Oper oper);

	String getOperRightsString(Oper oper); 
}
