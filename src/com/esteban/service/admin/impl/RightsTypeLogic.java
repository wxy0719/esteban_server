package com.esteban.service.admin.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.esteban.dao.RightsTypeDao;
import com.esteban.framework.utils.Page;
import com.esteban.model.RightsType;
import com.esteban.model.RightsTypeExample;
import com.esteban.service.admin.IRightsTypeLogic;

@Service
public class RightsTypeLogic implements IRightsTypeLogic{
	
	@Resource 
	private RightsTypeDao rightsTypeDao;
	
	
	public List<RightsType> listByPage(RightsType t, Page page) {
		return rightsTypeDao.listByPage(t, page);
	}

	
	public List<RightsType> detail(RightsTypeExample emp) {
		if(emp!=null){
			return rightsTypeDao.selectByExample(emp);
		}
		return null;
	}
	
	public RightsType detailFirst(RightsTypeExample emp){
		RightsType rt=null;
		List<RightsType> list=detail(emp);
		if(list!=null&&list.size()>0){
			rt=list.get(0);
		}
		return rt;
	}
	
	
	public RightsType detailFirstWithBlob(RightsTypeExample emp){
		RightsType rt=null;
		List<RightsType> list=detailWithBlob(emp);
		if(list!=null&&list.size()>0){
			rt=list.get(0);
		}
		return rt;
	}
	
	public List<RightsType> detailWithBlob(RightsTypeExample emp) {
		if(emp!=null){
			return rightsTypeDao.selectByExampleWithBLOBs(emp);
		}
		return null;
	}

	
	public boolean add(RightsType t) {
		int result=0;
		if(t!=null){
			result=rightsTypeDao.insert(t);
		}
		return result>0;
	}

	
	public boolean modifyAll(RightsType t,RightsTypeExample emp) {
		int result=0;
		if(t!=null){
			result=rightsTypeDao.updateByExample(t, emp);
		}
		return result>0;
	}
	
	
	public boolean modify(RightsType t,RightsTypeExample emp) {
		int result=0;
		if(t!=null){
			result=rightsTypeDao.updateByExampleSelective(t, emp);
		}
		return result>0;
	}

	
	public boolean delete(RightsTypeExample emp) {
		int result=0;
		if(emp!=null){
			result=rightsTypeDao.deleteByExample(emp);
		}
		return result>0;
	}


}
