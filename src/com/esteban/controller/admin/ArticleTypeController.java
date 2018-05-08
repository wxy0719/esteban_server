package com.esteban.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esteban.framework.annotation.Login;
import com.esteban.framework.utils.UUID;
import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.ArticleType;
import com.esteban.model.ArticleTypeExample;
import com.esteban.service.admin.IArticleTypeLogic;

@Controller
@RequestMapping("/admin/articleType")
public class ArticleTypeController {
	@Resource
	private IArticleTypeLogic articleTypeLogic;
	
	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/listArticleType")
	public String list(){
		return "admin/articleType/list_articleType";
	}
	
	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listArticleTypeJSON")
	public void listArticleTypeJSON(HttpServletRequest req,HttpServletResponse res,String name,String status){
		Page webPage=WebUtils.getUIPage(req);
		ArticleType at=new ArticleType();
		at.setName(name);
		List<ArticleType> list=articleTypeLogic.listByPage(at, webPage);
		String html=WebUtils.getTableJSON(list,webPage.getTotalRows());
		JSONObject obj=JSONObject.fromObject(html);
		try {
			res.setHeader("ContentType", "text/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(obj);
			res.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toAdd")
	public String toAdd(Model model,HttpServletRequest request){
		return "admin/articleType/add_articleType";
	}
	
	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toEdit")
	public String toEdit(String id,Model model,HttpServletRequest request){
		ArticleTypeExample artEmp=new ArticleTypeExample();
		artEmp.or().andIdEqualTo(id);
		ArticleType at=articleTypeLogic.detailFirst(artEmp);
		model.addAttribute("articleType",at);
		return "admin/articleType/edit_articleType";
	}
	
	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/add")
	public void add(HttpServletRequest req,HttpServletResponse res,ArticleType at){
		String info="添加成功";
		at.setId(UUID.getUUID());
		boolean flag=articleTypeLogic.add(at);
		if(!flag){
			info="添加失败";
		}
		try {
			res.setHeader("ContentType", "text/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(info);
			res.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/edit")
	public void edit(HttpServletRequest req,HttpServletResponse res,ArticleType at){
		String info="修改成功";
		ArticleTypeExample atEmp=new ArticleTypeExample();
		atEmp.or().andIdEqualTo(at.getId());
		boolean flag=articleTypeLogic.modify(at,atEmp);
		if(!flag){
			info="修改失败";
		}
		try {
			res.setHeader("ContentType", "text/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(info);
			res.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/del")
	public void del(HttpServletRequest req,HttpServletResponse res,String id){
		String info="删除成功";
		ArticleTypeExample atEmp=new ArticleTypeExample();
		atEmp.or().andIdEqualTo(id);
		boolean flag=articleTypeLogic.delete(atEmp);
		if(!flag){
			info="删除失败";
		}
		try {
			res.setHeader("ContentType", "text/json"); 
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(info);
			res.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
