package com.esteban.core.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esteban.core.system.model.Oper;
import com.esteban.core.system.model.OperExample;
import com.esteban.core.system.service.base.IBaseService;

public interface IOperLogic extends IBaseService<Oper, OperExample> {

	Map<String, String> login(Oper oper, String validateString, String deviceCode, HttpServletRequest req,
							  HttpServletResponse res);

	void saveLog(String operUser, String info, String remoteAddr);

	void updateUserLoginInfo(String mobile, String remoteAddr, String type,String loginTime, String provName, String areaName);
	
	List<String> getOperRights(Oper oper);

	String getOperRightsString(Oper oper); 
}
