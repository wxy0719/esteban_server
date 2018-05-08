package com.esteban.controller.front;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.Ads;
import com.esteban.model.Article;
import com.esteban.model.ArticleType;
import com.esteban.model.Agent;
import com.esteban.service.admin.IAdsLogic;
import com.esteban.service.admin.IArticleLogic;
import com.esteban.service.admin.IArticleTypeLogic;

/**
 * @author esteban
 * @since 2014�?5�?23�?
 */
@Controller
@RequestMapping("")
public class LoginController {

	@Resource
	private IArticleLogic articleLogic;

	@Resource
	private IAdsLogic adsLogic;

	@Resource
	private IArticleTypeLogic articleTypeLogic;

	@RequestMapping("/login")
	public String login(HttpSession session, String userId, String passwd) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(passwd)) {
			return "user/login";
		}

		Agent agent = new Agent();
		agent.setMobile(userId);
		session.setAttribute(WebUtils.AGENT_OPER, agent);
		String path = (String)session.getAttribute("RedirectPath");
		session.removeAttribute("RedirectPath");
		System.out.println("path:" + path);
		if(StringUtils.isNotBlank(path)){
			return "redirect:" + path;
		}
		return "redirect: /resource/app/7000/detail";
	}

	@RequestMapping("")
	public String homePage(HttpServletRequest request,Model model){
		Page page=WebUtils.getPage(request);

		//获取首页新闻
		page.setShowAll(true);
		ArticleType at=new ArticleType();
		List<ArticleType> listType=articleTypeLogic.listByPage(at, page);
		page.setShowAll(false);
		if(listType!=null&&listType.size()!=0){
			for(ArticleType type:listType){
				Article a=new Article();
				a.setType(type.getId());
				page.setShowRows(5);
				page.setCurrentPage(1);
				List<Article> list=articleLogic.listByPage(a, page);
				model.addAttribute(type.getCode(), list);
			}
		}

		//获取首页图片广告
		Ads ads=new Ads();
		page.setShowRows(5);
		page.setCurrentPage(1);
		List<Ads> listAds=adsLogic.listByPage(ads, page);
		model.addAttribute("ads", listAds);
		return "/front/index";
	}
}
