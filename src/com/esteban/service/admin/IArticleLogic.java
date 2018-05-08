package com.esteban.service.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.esteban.framework.utils.Page;
import com.esteban.model.Article;
import com.esteban.model.ArticleExample;
import com.esteban.service.base.BaseService;

public interface IArticleLogic extends BaseService<Article, ArticleExample>{

	Map<String,String> uploadImg(HttpServletRequest req);

	Map<String, String> uploadFile(HttpServletRequest req);

	Map<String, String> uploadVideo(HttpServletRequest req);

	List<String> listImgs(HttpServletRequest req);

	List<String> listFiles(HttpServletRequest req);

	List<Map<String, Object>> queryAllByPage(Article art, Page webPage);

}
