package com.esteban.core.system.service.impl;

import com.esteban.core.framework.utils.Page;
import com.esteban.core.system.dao.RightsDao;
import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.Rights;
import com.esteban.core.system.model.RightsExample;
import com.esteban.core.system.service.IRightsLogic;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RightsLogic extends BaseServiceImpl<Rights,RightsExample> implements IRightsLogic {

	@Resource
	private RightsDao rightsDao;


	@Override
	public IDao getDao() {
		return rightsDao;
	}

	public List<Map<String, Object>> listAllInfo(Rights rights, Page webPage) {
		return rightsDao.listAllInfo(rights,webPage);
	}

}
