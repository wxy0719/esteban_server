package com.esteban.business.service.newsManagement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.esteban.business.model.Wenku;
import com.esteban.business.model.WenkuExample;
import com.esteban.core.system.service.base.IBaseService;

public interface IWenKuLogic extends IBaseService<Wenku, WenkuExample> {

	List<String> listFiles(HttpServletRequest req);

	String addWenku(Wenku wenKu, HttpServletRequest req);

	String modifyWenku(Wenku wk, HttpServletRequest req);

}
