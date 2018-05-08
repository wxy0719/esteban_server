package com.esteban.dao.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.esteban.framework.utils.Page;

public interface IDao<T, K> {
    /**
     * 查询满足条件的记录
     */
    List<T> listByPage(@Param("t")T t, @Param("page") Page page);

    /**
     * 根据指定的角色ID查询详细信息
     */
    List<T> selectByExample(K k);

    /**
     * 添加
     */
    int insert(T t);

    /**
     * 修改
     */
    int updateByExample(@Param("record")T t,@Param("example")K k);

    /**
     * 删除
     */
    int deleteByExample(K k);

    long countByExample(K k);

    int insertSelective(T t);

    List<T> selectByExampleWithBLOBs(K k);

    int updateByExampleSelective(@Param("record") T t, @Param("example") K k);

    int updateByExampleWithBLOBs(@Param("record") T t, @Param("example") K k);

}