package com.esteban.core.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.framework.utils.Page;
import com.esteban.core.system.model.Rights;
import com.esteban.core.system.model.RightsExample;

public interface RightsDao extends IDao<Rights, RightsExample>{

	List<Map<String, Object>> listAllInfo(@Param("rights")Rights rights,@Param("page") Page webPage);
    
}