package com.esteban.core.system.dao;

import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.LoginLog;
import com.esteban.core.system.model.LoginLogExample;
import org.apache.ibatis.annotations.Param;

public interface LoginLogDao extends IDao<LoginLog,LoginLogExample>{

    LoginLog getLoginInfoByToken(@Param("token")String token);
}