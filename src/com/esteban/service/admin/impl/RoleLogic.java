package com.esteban.service.admin.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.esteban.dao.RoleDao;
import com.esteban.framework.utils.Page;
import com.esteban.model.Role;
import com.esteban.model.RoleExample;
import com.esteban.service.admin.IRoleLogic;

@Service
public class RoleLogic implements IRoleLogic {

	@Resource
	private RoleDao roleDao; 
	
	
	public List<Role> listByPage(Role t, Page page) {
		return roleDao.listByPage(t, page);
	}

	
	public List<Role> detail(RoleExample emp) {
		if(emp!=null){
			return roleDao.selectByExample(emp);
		}
		return null;
	}
	
	
	public List<Role> detailWithBlob(RoleExample emp) {
		if(emp!=null){
			return roleDao.selectByExampleWithBLOBs(emp);
		}
		return null;
	}
	
	public Role detailFirst(RoleExample emp){
		Role r=null;
		List<Role> list=detail(emp);
		if(list!=null&&list.size()>0){
			r=list.get(0);
		}
		return r;
	}
	
	
	public Role detailFirstWithBlob(RoleExample emp){
		Role r=null;
		List<Role> list=detailWithBlob(emp);
		if(list!=null&&list.size()>0){
			r=list.get(0);
		}
		return r;
	}

	
	public boolean add(Role t) {
		int result=0;
		if(t!=null){
			result=roleDao.insert(t);
		}
		return result>0;
	}

	
	public boolean modifyAll(Role t,RoleExample emp) {
		int result=0;
		if(t!=null){
			result=roleDao.updateByExample(t, emp);
		}
		return result>0;
	}
	
	
	public boolean modify(Role t,RoleExample emp) {
		int result=0;
		if(t!=null){
			result=roleDao.updateByExampleSelective(t, emp);
		}
		return result>0;
	}

	
	public boolean delete(RoleExample emp) {
		int result=0;
		if(emp!=null){
			result=roleDao.deleteByExample(emp);
		}
		return result>0;
	}

}
