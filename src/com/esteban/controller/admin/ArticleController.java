package com.esteban.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esteban.framework.annotation.Login;
import com.esteban.framework.utils.DateOperator;
import com.esteban.framework.utils.UUID;
import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.WebUtils;
import com.esteban.framework.utils.excelPlugin.ExcelKit;
import com.esteban.model.Article;
import com.esteban.model.ArticleExample;
import com.esteban.model.ArticleType;
import com.esteban.service.admin.IArticleLogic;
import com.esteban.service.admin.IArticleTypeLogic;

@Controller
@RequestMapping("/admin/article")
public class ArticleController {

	@Resource
	private IArticleLogic articleLogic;

	@Resource
	private IArticleTypeLogic articleTypeLogic;

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/listArticle")
	public String list(){
		return "admin/article/list_article";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toAdd")
	public String toAdd(HttpServletRequest request,Model model){
		ArticleType at=new ArticleType();
		Page page=WebUtils.getPage(request);
		page.setShowAll(true);
		List<ArticleType> list=articleTypeLogic.listByPage(at, page);
		model.addAttribute("artType", list);
		model.addAttribute("prjName", "esteban");
		return "admin/article/add_article";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toEdit")
	public String toEdit(String id,Model model,HttpServletRequest request){
		ArticleType at=new ArticleType();
		Page page=WebUtils.getPage(request);
		page.setShowAll(true);
		List<ArticleType> list=articleTypeLogic.listByPage(at, page);
		ArticleExample arEmp=new ArticleExample();
		arEmp.or().andIdEqualTo(id);
		Article art=articleLogic.detailFirstWithBlob(arEmp);
		model.addAttribute("artType", list);
		model.addAttribute("art",art);
		return "admin/article/edit_article";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/edit")
	public void edit(HttpServletRequest req,HttpServletResponse res,Article at){
		String info="修改成功";
		ArticleExample arEmp=new ArticleExample();
		arEmp.or().andIdEqualTo(at.getId());
		boolean flag=articleLogic.modify(at,arEmp);
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
		ArticleExample arEmp=new ArticleExample();
		arEmp.or().andIdEqualTo(id);
		boolean flag=articleLogic.delete(arEmp);
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

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listArticleJSON")
	public void listArticle(HttpServletRequest req,HttpServletResponse res,String content,String author){
		Page webPage=WebUtils.getUIPage(req);
		Article art=new Article();
		art.setTitle(req.getParameter("name"));
		List<Map<String,Object>> list=articleLogic.queryAllByPage(art, webPage);
		String html=WebUtils.getMapJSON(list,webPage.getTotalRows());
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
	@ResponseBody
	@RequestMapping("/ifm/add")
	public void add(HttpServletRequest req,HttpServletResponse res,Article art){
		String info="添加成功";
		art.setId(UUID.getUUID());
		if("1".equals(art.getStatus())){
			art.setPublictime(DateOperator.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		}else{
			art.setPublictime("");
		}
		art.setCreateuser(WebUtils.getOper(req).getId());
		boolean flag=articleLogic.add(art);
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

	@Login(WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/upload")
	public void uploadImgs(HttpServletRequest req,HttpServletResponse res,String action){
		String info="{";
		if("uploadImg".equals(action)){
			Map<String,String> map=articleLogic.uploadImg(req);
			info+="'state':'"+map.get("state")+"','url':'"+map.get("url")+"','title':'"+map.get("title")+"','original':'"+map.get("original")+"'";
		}else if("uploadFile".equals(action)){
			Map<String,String> map=articleLogic.uploadFile(req);
			info+="'state':'"+map.get("state")+"','url':'"+map.get("url")+"','original':'"+map.get("original")+"'";
		}else if("uploadVideo".equals(action)){
			Map<String,String> map=articleLogic.uploadVideo(req);
			info+="'state':'"+map.get("state")+"','url':'"+map.get("url")+"','original':'"+map.get("original")+"'";
		}
		info+="}";
		JSONObject jsonObject = JSONObject.fromObject(info);
		try {
			res.setHeader("ContentType", "text/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(jsonObject);
			res.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Login(WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listImgs")
	public void listImgs(HttpServletRequest req,HttpServletResponse res){
		Map<String,Object> map=new HashMap<String, Object>();
		List<String> list=new ArrayList<String>();
		list=articleLogic.listImgs(req);
		map.put("list", list);
		map.put("state", "SUCCESS");
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.setHeader("ContentType", "text/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(jsonObject.toString());
			res.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Login(WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listFiles")
	public void listFiles(HttpServletRequest req,HttpServletResponse res){
		Map<String,Object> map=new HashMap<String, Object>();
		List<String> list=new ArrayList<String>();
		list=articleLogic.listFiles(req);
		map.put("list", list);
		map.put("state", "SUCCESS");
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.setHeader("ContentType", "text/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(jsonObject.toString());
			res.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Login(WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/excel")
	public void excel(HttpServletRequest req,HttpServletResponse res){
		Page webPage=WebUtils.getUIPage(req);
		webPage.setShowAll(true);
		Article art=new Article();
		art.setTitle(req.getParameter("name"));
		List<Map<String,Object>> list=articleLogic.queryAllByPage(art, webPage);
		ExcelKit.$Export(res).toExcelMap(list, null, "文章详情导出", "queryArticle");
	}
}
