package com.esteban.service.front.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.esteban.dao.AdsDao;
import com.esteban.framework.utils.Page;
import com.esteban.model.Ads;
import com.esteban.model.AdsExample;
import com.esteban.service.front.IIndexService;

@Service
public class IndexServiceImpl implements IIndexService{
	
	@Resource
	private AdsDao adsDao;

	public boolean add(Ads t) {
		int i=adsDao.insert(t);
		return i>0;
	}

	public boolean delete(AdsExample emp) {
		return adsDao.deleteByExample(emp)>0;
	}

	public List<Ads> detail(AdsExample emp) {
		if(emp!=null){
			return adsDao.selectByExampleWithBLOBs(emp);
		}
		return null;
	}

	public List<Ads> listByPage(Ads t, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean modify(Ads t, AdsExample k) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean modifyAll(Ads t, AdsExample k) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Ads> detailWithBlob(AdsExample k) {
		// TODO Auto-generated method stub
		return null;
	}

	public Ads detailFirst(AdsExample k) {
		// TODO Auto-generated method stub
		return null;
	}

	public Ads detailFirstWithBlob(AdsExample k) {
		// TODO Auto-generated method stub
		return null;
	}

}
