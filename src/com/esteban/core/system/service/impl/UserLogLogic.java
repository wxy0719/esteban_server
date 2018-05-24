package com.esteban.core.system.service.impl;

import com.esteban.core.system.dao.UserLogDao;
import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.UserLog;
import com.esteban.core.system.model.UserLogExample;
import com.esteban.core.system.service.IUserLogLogic;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserLogLogic extends BaseServiceImpl<UserLog,UserLogExample> implements IUserLogLogic {
	
	@Resource
	private UserLogDao userLogDao;

	@Override
	public IDao getDao() {
		return userLogDao;
	}

}
