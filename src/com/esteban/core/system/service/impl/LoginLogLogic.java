package com.esteban.core.system.service.impl;

import com.esteban.core.system.dao.LoginLogDao;
import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.LoginLog;
import com.esteban.core.system.model.LoginLogExample;
import com.esteban.core.system.service.ILoginLogLogic;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by CPR269 on 2018/5/25.
 */
@Service
public class LoginLogLogic extends BaseServiceImpl<LoginLog,LoginLogExample> implements ILoginLogLogic {

    @Resource
    private LoginLogDao loginLogDao;

    @Override
    public IDao getDao() {
        return loginLogDao;
    }
}
