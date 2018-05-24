package com.esteban.core.system.service.impl;

import com.esteban.core.system.dao.ConfigDao;
import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.Config;
import com.esteban.core.system.model.ConfigExample;
import com.esteban.core.system.service.IConfigLogic;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConfigLogic extends BaseServiceImpl<Config,ConfigExample> implements IConfigLogic {

	@Resource
	private ConfigDao configDao;

	@Override
	public IDao getDao() {
		return configDao;
	}

	public Config queryConfigByName(String name) {
		Config config=null;
		if(!StringUtils.isBlank(name)){
			ConfigExample emp=new ConfigExample();
			emp.or().andNameLike(name);
			List<Config> list=detail(emp);
			if(list!=null&&list.size()>0){
				config=list.get(0);
			}
		}
		return config;
	}

}
