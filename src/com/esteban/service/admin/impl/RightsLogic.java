package com.esteban.service.admin.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.esteban.dao.RightsDao;
import com.esteban.framework.utils.Page;
import com.esteban.model.Rights;
import com.esteban.model.RightsExample;
import com.esteban.service.admin.IRightsLogic;

@Service
public class RightsLogic implements IRightsLogic {

	@Resource
	private RightsDao rightsDao;
	
	
	public List<Rights> listByPage(Rights t, Page page) {
			return rightsDao.listByPage(t, page);
	}

	
	public List<Rights> detail(RightsExample emp) {
		if(emp!=null){
			return rightsDao.selectByExample(emp);
		}
		return null;
	}
	
	
	public List<Rights> detailWithBlob(RightsExample emp) {
		if(emp!=null){
			return rightsDao.selectByExampleWithBLOBs(emp);
		}
		return null;
	}
	
	public Rights detailFirst(RightsExample emp){
		Rights r=null;
		List<Rights> listRi=detail(emp);
		if(listRi!=null&&listRi.size()>0){
			r=listRi.get(0);
		}
		return r;
	}
	
	
	public Rights detailFirstWithBlob(RightsExample emp){
		Rights r=null;
		List<Rights> listRi=detailWithBlob(emp);
		if(listRi!=null&&listRi.size()>0){
			r=listRi.get(0);
		}
		return r;
	}

	
	public boolean add(Rights t) {
		int result=0;
		if(t!=null){
			result=rightsDao.insert(t);
		}
		return result>0;
	}

	
	public boolean modifyAll(Rights t,RightsExample emp) {
		int result=0;
		if(t!=null){ 
			result=rightsDao.updateByExample(t,emp);
		}
		return result>0;
	}
	
	
	public boolean modify(Rights t,RightsExample emp) {
		int result=0;
		if(t!=null){ 
			result=rightsDao.updateByExampleSelective(t,emp);
		}
		return result>0;
	}

	
	public boolean delete(RightsExample emp) {
		int result=0;
		if(emp!=null){
			result=rightsDao.deleteByExample(emp);
		}
		return result>0;
	}


	public List<Map<String, Object>> listAllInfo(Rights rights, Page webPage) {
		return rightsDao.listAllInfo(rights,webPage);
	}

}
