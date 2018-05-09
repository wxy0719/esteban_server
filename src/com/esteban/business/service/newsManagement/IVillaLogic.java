package com.esteban.business.service.newsManagement;

import javax.servlet.http.HttpServletRequest;

import com.esteban.business.model.Villa;
import com.esteban.business.model.VillaExample;
import com.esteban.core.system.service.base.IBaseService;

public interface IVillaLogic extends IBaseService<Villa, VillaExample> {

	String addVilla(Villa villa, HttpServletRequest req);

	String modifyVilla(Villa villa, HttpServletRequest req);

}
