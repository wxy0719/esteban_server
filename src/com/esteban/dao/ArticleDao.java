package com.esteban.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.esteban.dao.base.IDao;
import com.esteban.framework.utils.Page;
import com.esteban.model.Article;
import com.esteban.model.ArticleExample;

public interface ArticleDao extends IDao<Article, ArticleExample>{

	List<Map<String, Object>> queryAllByPage(@Param("t")Article art, @Param("page")Page webPage);
    
}
