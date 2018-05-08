package com.esteban.controller.front;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esteban.model.Article;
import com.esteban.model.ArticleExample;
import com.esteban.service.admin.IArticleLogic;

@Controller("frontArticle")
@RequestMapping("/front/article")
public class ArticleController {

	@Resource
	private IArticleLogic articleLogic;

	@RequestMapping("/{articleID}")
	public String detail(HttpServletRequest req,@PathVariable String articleID,Model model){
		Article a=null;
		ArticleExample arEmp=new ArticleExample();
		arEmp.or().andIdEqualTo(articleID);
		List<Article> list_=articleLogic.detailWithBlob(arEmp);
		if(list_!=null&&list_.size()>0){
			a=list_.get(0);
		}

		model.addAttribute("article", a);
		return "/front/article/detail";
	}

}
