package com.esteban.core.system.dao;

import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.LoginLog;
import com.esteban.core.system.model.LoginLogExample;
import java.util.List;

import com.esteban.core.system.model.MenuTree;
import com.esteban.core.system.model.MenuTreeExample;
import org.apache.ibatis.annotations.Param;

public interface LoginLogDao extends IDao<MenuTree, MenuTreeExample> {

    LoginLog getLoginInfoByToken(@Param("token")String token);

}