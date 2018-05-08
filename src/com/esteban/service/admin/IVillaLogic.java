package com.esteban.service.admin;

import javax.servlet.http.HttpServletRequest;

import com.esteban.model.Villa;
import com.esteban.model.VillaExample;
import com.esteban.service.base.BaseService;

public interface IVillaLogic extends BaseService<Villa, VillaExample>{

	String addVilla(Villa villa, HttpServletRequest req);

	String modifyVilla(Villa villa, HttpServletRequest req);

}
