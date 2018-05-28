package com.esteban.core.system.service;

import java.util.List;
import java.util.Map;

import com.esteban.core.framework.utils.Page;
import com.esteban.core.system.model.Rights;
import com.esteban.core.system.model.RightsExample;
import com.esteban.core.system.service.base.IBaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IRightsLogic extends IBaseService<Rights, RightsExample> {

	List<Map<String, Object>> listAllInfo(Rights rights, Page webPage);

    public Object getRights(HttpServletRequest request, HttpServletResponse response);

}
