package com.esteban.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
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
import com.esteban.framework.utils.UUID;
import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.Wenku;
import com.esteban.model.WenkuExample;
import com.esteban.service.admin.IWenKuLogic;

@Controller
@RequestMapping("/admin/wenku")
public class WenKuController {

	@Resource
	private IWenKuLogic wenKuLogic;

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toAdd")
	public String toAdd(HttpServletRequest request,Model model){
		return "admin/wenku/add_wenku";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toEdit")
	public String toEdit(String id,Model model,HttpServletRequest request){
		WenkuExample wkEmp=new WenkuExample();
		wkEmp.or().andAuthorEqualTo(id);
		Wenku wenKu=wenKuLogic.detailFirst(wkEmp);

		model.addAttribute("wenku",wenKu);
		return "admin/wenku/edit_wenku";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/edit")
	public String edit(HttpServletRequest req,HttpServletResponse res,Wenku wk,Model model){
		String info="修改成功";
		String flag=wenKuLogic.modifyWenku(wk,req);
		if(!info.equals(flag)){
			info=flag;
		}
		model.addAttribute("msg", info);
		return "admin/wenku/ifm";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/del")
	public void del(HttpServletRequest req,HttpServletResponse res,String id){
		String info="删除成功";
		WenkuExample wkEmp=new WenkuExample();
		wkEmp.or().andAuthorEqualTo(id);
		boolean flag=wenKuLogic.delete(wkEmp);
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
	@RequestMapping("/ifm/listWenKu")
	public String list(){
		return "admin/wenku/list_wenku";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listWenKuJSON")
	public void listWenKu(HttpServletRequest req,HttpServletResponse res,String content,String author){
		Page webPage=WebUtils.getUIPage(req);
		Wenku wenKu=new Wenku();
		wenKu.setTitle(content);
		wenKu.setAuthor(author);
		List<Wenku> list=wenKuLogic.listByPage(wenKu, webPage);
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
	@RequestMapping("/ifm/add")
	public String add(HttpServletRequest req,HttpServletResponse res,Wenku wenKu,Model model){
		String info="添加成功";
		wenKu.setId(UUID.getUUID());
		wenKu.setCreateuser(WebUtils.getOper(req).getId());
		String flag=wenKuLogic.addWenku(wenKu,req);
		if(!info.equals(flag)){
			info=flag;
		}
		model.addAttribute("msg", info);
		return "admin/wenku/ifm";
	}

	@Login(WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listImgs")
	public void listImgs(HttpServletRequest req,HttpServletResponse res){
		Map<String,Object> map=new HashMap<String, Object>();
		List<String> list=new ArrayList<String>();
		list=wenKuLogic.listFiles(req);
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
}
