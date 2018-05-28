package com.esteban.core.system.service.impl;

import com.esteban.core.framework.utils.DateOperator;
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

    @Override
    public LoginLog checkTokenIsValid(String token) {
        LoginLog log=null;

        log = loginLogDao.getLoginInfoByToken(token);

        //验证失败返回false，验证成功返回true，并更新数据库中的时间，保持连续操作的有效时间。
        if(log!=null){
            log.setTime(DateOperator.getNowDate());

            //查询该用户是否有 在有效期内的登录记录
            LoginLogExample logExm = new LoginLogExample();
            logExm.createCriteria().andTokenEqualTo(token);
            update(log,logExm);
        }

        return log;
    }
}
