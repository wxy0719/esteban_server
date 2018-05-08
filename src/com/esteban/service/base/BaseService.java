package com.esteban.service.base;

import java.util.List;

import com.esteban.framework.utils.Page;

public interface BaseService<T, K> {
    /**
     * 查询所有信息,带分页信息
     */
    List<T> listByPage(T t, Page page);

    /**
     * 查询详细信息
     */
    List<T> detail(K k);

    /**
     * 查询详细信息
     */
    List<T> detailWithBlob(K k);

    /**
     * 查询详细信息
     */
    T detailFirst(K k);

    /**
     * 查询详细信息
     */
    T detailFirstWithBlob(K k);

    /**
     * 添加
     */
    boolean add(T t);

    /**
     * 修改
     */
    boolean modify(T t,K k);

    /**
     * 修改
     */
    boolean modifyAll(T t,K k);

    /**
     * 删除
     */
    boolean delete(K k);
}
