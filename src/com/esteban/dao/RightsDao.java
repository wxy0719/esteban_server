package com.esteban.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.esteban.dao.base.IDao;
import com.esteban.framework.utils.Page;
import com.esteban.model.Rights;
import com.esteban.model.RightsExample;

public interface RightsDao extends IDao<Rights, RightsExample>{

	List<Map<String, Object>> listAllInfo(@Param("rights")Rights rights,@Param("page") Page webPage);
    
}