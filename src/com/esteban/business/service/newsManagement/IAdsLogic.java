package com.esteban.business.service.newsManagement;

import javax.servlet.http.HttpServletRequest;

import com.esteban.business.model.Ads;
import com.esteban.business.model.AdsExample;
import com.esteban.core.system.service.base.IBaseService;

public interface IAdsLogic extends IBaseService<Ads, AdsExample> {

	String addAds(Ads ads, HttpServletRequest req);

	boolean deleteAds(String id, HttpServletRequest req);

	String editAds(Ads ads, HttpServletRequest req);

}
 