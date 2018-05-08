package com.esteban.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.esteban.dao.base.IDao;
import com.esteban.model.MenuTree;
import com.esteban.model.MenuTreeExample;

public interface MenuTreeDao extends IDao<MenuTree, MenuTreeExample>{

	public List<MenuTree> queryMenuTree(@Param("grade")String grade,@Param("parentNode") String parentNode,@Param("rights") List<String> rights);
    
}