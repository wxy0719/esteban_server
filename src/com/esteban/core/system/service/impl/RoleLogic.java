package com.esteban.core.system.service.impl;

import com.esteban.core.system.dao.RoleDao;
import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.Role;
import com.esteban.core.system.model.RoleExample;
import com.esteban.core.system.service.IRoleLogic;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleLogic extends BaseServiceImpl<Role,RoleExample> implements IRoleLogic {

	@Resource
	private RoleDao roleDao;


	@Override
	public IDao getDao() {
		return roleDao;
	}

}
