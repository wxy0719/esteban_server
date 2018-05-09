package com.esteban.business.service.newsManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.esteban.core.framework.utils.Page;
import com.esteban.business.model.Article;
import com.esteban.business.model.ArticleExample;
import com.esteban.core.system.service.base.IBaseService;

public interface IArticleLogic extends IBaseService<Article, ArticleExample> {

	Map<String,String> uploadImg(HttpServletRequest req);

	Map<String, String> uploadFile(HttpServletRequest req);

	Map<String, String> uploadVideo(HttpServletRequest req);

	List<String> listImgs(HttpServletRequest req);

	List<String> listFiles(HttpServletRequest req);

	List<Map<String, Object>> queryAllByPage(Article art, Page webPage);

}
