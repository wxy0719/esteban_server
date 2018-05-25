package com.esteban.business.service.newsManagement.impl;

import java.util.List;

import javax.annotation.Resource;

import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import com.esteban.business.dao.ArticleTypeDao;
import com.esteban.core.framework.utils.Page;
import com.esteban.business.model.ArticleType;
import com.esteban.business.model.ArticleTypeExample;
import com.esteban.business.service.newsManagement.IArticleTypeLogic;

@Service
public class ArticleTypeLoigc extends BaseServiceImpl<ArticleType,ArticleTypeExample> implements IArticleTypeLogic {
	
	@Resource
	private ArticleTypeDao articleTypeDao;


	@Override
	public IDao getDao() {
		return articleTypeDao;
	}

}
