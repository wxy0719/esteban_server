package com.esteban.core.system.service.base.impl;

import com.esteban.core.framework.utils.Page;
import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.service.base.IBaseService;

import java.util.List;

/**
 * Created by CPR269 on 2018/5/24.
 */
public abstract class BaseServiceImpl<T,K> implements IBaseService<T,K> {

    public abstract IDao getDao();

    @Override
    public List<T> listByPage(T t, Page page) {
        return getDao().listByPage(t, page);
    }

    @Override
    public List<T> detail(K k) {
        if(k!=null){
            return getDao().selectByExample(k);
        }
        return null;
    }

    @Override
    public List<T> detailWithBlob(K k) {
        if(k!=null){
            return getDao().selectByExampleWithBLOBs(k);
        }
        return null;
    }

    @Override
    public T detailFirst(K k) {
        T t=null;
        List<T> list=detail(k);
        if(list!=null&&list.size()>0){
            t=list.get(0);
        }
        return t;
    }

    @Override
    public T detailFirstWithBlob(K k) {
        T t=null;
        List<T> list=detailWithBlob(k);
        if(list!=null&&list.size()>0){
            t=list.get(0);
        }
        return t;
    }

    @Override
    public boolean insert(T t) {
        int result=0;
        if(t!=null){
            result=getDao().insert(t);
        }
        return result>0;
    }

    @Override
    public boolean update(T t, K k) {
        int result=0;
        if(t!=null){
            result=getDao().updateByExampleSelective(t,k);
        }
        return result>0;
    }

    @Override
    public boolean updateAll(T t, K k) {
        int result=0;
        if(t!=null){
            result=getDao().updateByExample(t,k);
        }
        return result>0;
    }

    @Override
    public boolean delete(K k) {
        int result=0;
        if(k!=null){
            result=getDao().deleteByExample(k);
        }
        return result>0;
    }
}
