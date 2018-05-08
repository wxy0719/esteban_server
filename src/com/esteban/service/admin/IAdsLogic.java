package com.esteban.service.admin;

import javax.servlet.http.HttpServletRequest;

import com.esteban.model.Ads;
import com.esteban.model.AdsExample;
import com.esteban.service.base.BaseService;

public interface IAdsLogic extends BaseService<Ads, AdsExample> {

	String addAds(Ads ads, HttpServletRequest req);

	boolean deleteAds(String id, HttpServletRequest req);

	String editAds(Ads ads, HttpServletRequest req);

}
 