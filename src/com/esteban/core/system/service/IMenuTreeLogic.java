package com.esteban.core.system.service;

import java.util.List;
import java.util.Map;

import com.esteban.core.system.model.MenuTree;
import com.esteban.core.system.model.MenuTreeExample;
import com.esteban.core.system.service.base.IBaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IMenuTreeLogic extends IBaseService<MenuTree, MenuTreeExample> {
	
	public List<MenuTree> queryTreeMenu(String parentNode, List<String> rights);

    public Object getSubMenuByParentId(HttpServletRequest request, HttpServletResponse response);

    /**
     * 查询所有菜单数据的父节点
     * @param isShowAllData  (true显示所有数据，false排除失效数据)
     * @return
     */
    List<Map<String,String>> getDistinctParentId(boolean isShowAllData);
}
