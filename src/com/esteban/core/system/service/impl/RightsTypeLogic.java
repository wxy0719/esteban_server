package com.esteban.core.system.service.impl;

import com.esteban.core.system.dao.RightsTypeDao;
import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.RightsType;
import com.esteban.core.system.model.RightsTypeExample;
import com.esteban.core.system.service.IRightsTypeLogic;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RightsTypeLogic extends BaseServiceImpl<RightsType,RightsTypeExample> implements IRightsTypeLogic{
	
	@Resource 
	private RightsTypeDao rightsTypeDao;


	@Override
	public IDao getDao() {
		return rightsTypeDao;
	}

}
