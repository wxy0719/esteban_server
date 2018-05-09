package com.esteban.core.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.MenuTree;
import com.esteban.core.system.model.MenuTreeExample;

public interface MenuTreeDao extends IDao<MenuTree, MenuTreeExample>{

	public List<MenuTree> queryMenuTree(@Param("grade")String grade,@Param("parentNode") String parentNode,@Param("rights") List<String> rights);
    
}