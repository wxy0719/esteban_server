package com.esteban.core.system.service;

import java.util.List;

import com.esteban.core.system.model.MenuTree;
import com.esteban.core.system.model.MenuTreeExample;
import com.esteban.core.system.service.base.IBaseService;

public interface IMenuTreeLogic extends IBaseService<MenuTree, MenuTreeExample> {
	
	public List<MenuTree> queryTreeMenu(String grade,String parentNode, List<String> rights);

}
