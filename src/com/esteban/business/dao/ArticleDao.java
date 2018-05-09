package com.esteban.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.framework.utils.Page;
import com.esteban.business.model.Article;
import com.esteban.business.model.ArticleExample;

public interface ArticleDao extends IDao<Article, ArticleExample>{

	List<Map<String, Object>> queryAllByPage(@Param("t")Article art, @Param("page")Page webPage);
    
}
