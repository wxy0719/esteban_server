package com.esteban.service.admin.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.esteban.dao.MenuTreeDao;
import com.esteban.framework.utils.Page;
import com.esteban.model.MenuTree;
import com.esteban.model.MenuTreeExample;
import com.esteban.service.admin.IMenuTreeLogic;

@Service
public class MenuTreeLogic implements IMenuTreeLogic {

	@Resource
	private MenuTreeDao menuTreeDao;

	
	public List<MenuTree> listByPage(MenuTree t, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<MenuTree> detail(MenuTreeExample emp) {
		if(emp!=null){
			return menuTreeDao.selectByExample(emp);
		}
		return null;
	}
	
	public MenuTree detailFirst(MenuTreeExample emp){
		MenuTree mt=null;
		List<MenuTree> listMenu=detail(emp);
		if(listMenu!=null&&listMenu.size()>0){
			mt=listMenu.get(0);
		}
		return mt;
	}
	
	public MenuTree detailFirstWithBlob(MenuTreeExample emp){
		MenuTree mt=null;
		List<MenuTree> listMenu=detailWithBlob(emp);
		if(listMenu!=null&&listMenu.size()>0){
			mt=listMenu.get(0);
		}
		return mt;
	}
	
	public List<MenuTree> detailWithBlob(MenuTreeExample emp) {
		if(emp!=null){
			return menuTreeDao.selectByExampleWithBLOBs(emp);
		}
		return null;
	}

	
	public boolean add(MenuTree t) {
		int result=0;
		if(t!=null){
			result=menuTreeDao.insert(t);
		}
		return result>0;
	}

	
	public boolean modifyAll(MenuTree t,MenuTreeExample emp) {
		int result=0;
		if(t!=null){
			result=menuTreeDao.updateByExample(t, emp);
		}
		return result>0;
	}
	
	
	public boolean modify(MenuTree t,MenuTreeExample emp) {
		int result=0;
		if(t!=null){
			result=menuTreeDao.updateByExampleSelective(t, emp);
		}
		return result>0;
	}

	
	public boolean delete(MenuTreeExample emp) {
		int result=0;
		if(emp!=null){
			result=menuTreeDao.deleteByExample(emp);
		}
		return result>0;
	}


	public List<MenuTree> queryTreeMenu(String grade, String parentNode, List<String> rights) {
		return menuTreeDao.queryMenuTree(grade,parentNode,rights);
	}

}
