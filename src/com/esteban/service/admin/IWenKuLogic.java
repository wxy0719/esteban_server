package com.esteban.service.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.esteban.model.Wenku;
import com.esteban.model.WenkuExample;
import com.esteban.service.base.BaseService;

public interface IWenKuLogic extends BaseService<Wenku, WenkuExample> {

	List<String> listFiles(HttpServletRequest req);

	String addWenku(Wenku wenKu, HttpServletRequest req);

	String modifyWenku(Wenku wk, HttpServletRequest req);

}
