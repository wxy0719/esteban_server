package com.esteban.service.admin.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.esteban.dao.ArticleTypeDao;
import com.esteban.framework.utils.Page;
import com.esteban.model.ArticleType;
import com.esteban.model.ArticleTypeExample;
import com.esteban.service.admin.IArticleTypeLogic;

@Service
public class ArticleTypeLoigc implements IArticleTypeLogic {
	
	@Resource
	private ArticleTypeDao articleTypeDao;

	
	public List<ArticleType> listByPage(ArticleType t, Page page) {
		return articleTypeDao.listByPage(t, page);
	}

	
	public List<ArticleType> detail(ArticleTypeExample emp) {
		if(emp!=null){
			return articleTypeDao.selectByExample(emp);
		}
		return null;
	}
	
	
	public List<ArticleType> detailWithBlob(ArticleTypeExample emp) {
		if(emp!=null){
			return articleTypeDao.selectByExampleWithBLOBs(emp);
		}
		return null;
	}
	
	public ArticleType detailFirst(ArticleTypeExample emp){
		ArticleType arType=null;
		List<ArticleType> list=detail(emp);
		if(list!=null&&list.size()>0){
			arType=list.get(0);
		}
		return arType;
	}
	
	public ArticleType detailFirstWithBlob(ArticleTypeExample emp){
		ArticleType arType=null;
		List<ArticleType> list=detailWithBlob(emp);
		if(list!=null&&list.size()>0){
			arType=list.get(0);
		}
		return arType;
	}

	
	public boolean add(ArticleType t) {
		int result=0;
		if(t!=null){
			result=articleTypeDao.insert(t);
		}
		return result>0;
	}

	
	public boolean modifyAll(ArticleType t,ArticleTypeExample emp) {
		int result=0;
		if(t!=null){
			result=articleTypeDao.updateByExample(t,emp);
		}
		return result>0;
	}
	
	public boolean modify(ArticleType t,ArticleTypeExample emp) {
		int result=0;
		if(t!=null){
			result=articleTypeDao.updateByExampleSelective(t,emp);
		}
		return result>0;
	}

	
	public boolean delete(ArticleTypeExample emp) {
		int result=0;
		if(emp!=null){
			result=articleTypeDao.deleteByExample(emp);
		}
		return result>0;
	}

}
