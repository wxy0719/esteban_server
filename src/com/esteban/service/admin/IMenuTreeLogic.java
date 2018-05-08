package com.esteban.service.admin;

import java.util.List;

import com.esteban.model.MenuTree;
import com.esteban.model.MenuTreeExample;
import com.esteban.service.base.BaseService;

public interface IMenuTreeLogic extends BaseService<MenuTree, MenuTreeExample>{
	
	public List<MenuTree> queryTreeMenu(String grade,String parentNode, List<String> rights);

}
