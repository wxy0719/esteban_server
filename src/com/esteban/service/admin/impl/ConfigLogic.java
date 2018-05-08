package com.esteban.service.admin.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.esteban.dao.ConfigDao;
import com.esteban.framework.utils.Page;
import com.esteban.model.Config;
import com.esteban.model.ConfigExample;
import com.esteban.service.admin.IConfigLogic;

@Service
public class ConfigLogic implements IConfigLogic {
	@Resource
	private ConfigDao configDao;

	
	public List<Config> listByPage(Config t, Page page) {
		return configDao.listByPage(t, page);
	}

	
	public List<Config> detail(ConfigExample emp) {
		if(emp!=null){
			return configDao.selectByExample(emp);
		}
		return null;
	}
	
	public List<Config> detailWithBlob(ConfigExample emp) {
		if(emp!=null){
			return configDao.selectByExampleWithBLOBs(emp);
		}
		return null;
	}
	
	public Config detailFirst(ConfigExample emp){
		Config con=null;
		List<Config> list=detail(emp);
		if(list!=null&&list.size()>0){
			con=list.get(0);
		}
		return con;
	}
	
	
	public Config detailFirstWithBlob(ConfigExample emp){
		Config con=null;
		List<Config> list=detailWithBlob(emp);
		if(list!=null&&list.size()>0){
			con=list.get(0);
		}
		return con;
	}

	
	public boolean add(Config t) {
		int result=0;
		if(t!=null){
			result=configDao.insert(t);
		}
		return result>0;
	}

	
	public boolean modifyAll(Config t,ConfigExample emp) {
		int result=0;
		if(t!=null){
			result=configDao.updateByExample(t,emp);
		}
		return result>0;
	}
	
	public boolean modify(Config t,ConfigExample emp) {
		int result=0;
		if(t!=null){
			result=configDao.updateByExampleSelective(t,emp);
		}
		return result>0;
	}

	
	public boolean delete(ConfigExample emp) {
		int result=0;
		if(emp!=null){
			result=configDao.deleteByExample(emp);
		}
		return result>0;
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
