package com.esteban.core.system.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import com.esteban.core.system.dao.MenuTreeDao;
import com.esteban.core.framework.utils.Page;
import com.esteban.core.system.model.MenuTree;
import com.esteban.core.system.model.MenuTreeExample;
import com.esteban.core.system.service.IMenuTreeLogic;

@Service
public class MenuTreeLogic extends BaseServiceImpl<MenuTree,MenuTreeExample> implements IMenuTreeLogic {

	@Resource
	private MenuTreeDao menuTreeDao;

	@Override
	public IDao getDao() {
		return menuTreeDao;
	}

	public List<MenuTree> queryTreeMenu(String grade, String parentNode, List<String> rights) {
		return menuTreeDao.queryMenuTree(grade,parentNode,rights);
	}

}
