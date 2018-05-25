package com.esteban.core.system.service.impl;

import com.esteban.core.system.dao.OperateLogDao;
import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.OperateLog;
import com.esteban.core.system.model.OperateLogExample;
import com.esteban.core.system.service.IOperateLogLogic;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OperateLogLogic extends BaseServiceImpl<OperateLog,OperateLogExample> implements IOperateLogLogic {
	
	@Resource
	private OperateLogDao operateLogDao;

	@Override
	public IDao getDao() {
		return operateLogDao;
	}

}
