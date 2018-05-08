package com.esteban.service.admin.impl;

import java.util.List;

import javax.annotation.Resource;

import com.esteban.dao.UserLogDao;
import com.esteban.framework.utils.Page;
import com.esteban.model.UserLog;
import com.esteban.model.UserLogExample;
import com.esteban.service.admin.IUserLogLogic;

public class UserLogLogic implements IUserLogLogic {
	
	@Resource
	private UserLogDao userLogDao;

	public List<UserLog> listByPage(UserLog t, Page page) {
		return userLogDao.listByPage(t, page);
	}

	public List<UserLog> detail(UserLogExample k) {
		if(k!=null){
			return userLogDao.selectByExample(k);
		}
		return null;
	}
	
	
	public List<UserLog> detailWithBlob(UserLogExample k) {
		if(k!=null){
			return userLogDao.selectByExampleWithBLOBs(k);
		}
		return null;
	}
	
	public UserLog detailFirst(UserLogExample k){
		return null;
	}
	
	public UserLog detailFirstWithBlob(UserLogExample k){
		return null;
	}

	public boolean add(UserLog t) {
		if(t!=null){
			return userLogDao.insert(t)>0;
		}
		return false;
	}

	public boolean modifyAll(UserLog t, UserLogExample k) {
		if(t!=null){
			return userLogDao.updateByExample(t, k)>0;
		}
		return false;
	}
	
	
	public boolean modify(UserLog t, UserLogExample k) {
		if(t!=null){
			return userLogDao.updateByExampleSelective(t, k)>0;
		}
		return false;
	}

	public boolean delete(UserLogExample k) {
		if(k!=null){
			return userLogDao.deleteByExample(k)>0;
		}
		return false;
	}

}
