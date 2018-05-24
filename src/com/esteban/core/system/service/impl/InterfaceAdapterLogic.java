package com.esteban.core.system.service.impl;

import com.esteban.core.system.dao.InterfaceAdapterDao;
import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.InterfaceAdapter;
import com.esteban.core.system.model.InterfaceAdapterExample;
import com.esteban.core.system.service.IInterfaceAdapterLogic;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by CPR269 on 2018/5/23.
 */
@Service
public class InterfaceAdapterLogic extends BaseServiceImpl<InterfaceAdapter,InterfaceAdapterExample> implements IInterfaceAdapterLogic {

    @Resource
    private InterfaceAdapterDao InterfaceAdapterDao;

    @Override
    public IDao getDao() {
        return InterfaceAdapterDao;
    }
}
