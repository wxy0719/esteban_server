package com.esteban.controller.front;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.Villa;
import com.esteban.service.admin.IVillaLogic;

@Controller("frontVillaController")
@RequestMapping("/front/villa")
public class VillaController {

	@Resource
	private IVillaLogic villaLogic;

	@RequestMapping("/listVilla")
	public String listVilla(HttpServletRequest req,Model model){
		Page page=WebUtils.getUIPage(req);
		page.setShowRows(4);
		String flag="1";
		Villa villa=new Villa();
		List<Villa> list=villaLogic.listByPage(villa, page);
		if(page.getTotalPages()==page.getCurrentPage()){
			flag="0";
		}
		model.addAttribute("list", list);
		model.addAttribute("flag", flag);
		return "/front/villa/list";
	}

	@RequestMapping("/ifm/listVillaJSON")
	public void listViallJSON(HttpServletRequest req,HttpServletResponse res){
		Page page=WebUtils.getUIPage(req);
		page.setShowRows(4);
		Villa villa=new Villa();
		List<Villa> list=villaLogic.listByPage(villa, page);
		String json="";
		for(Villa v:list){
			json+=v.getPath()+":"+v.getTitle()+",";
		}
		if(json.endsWith(",")){
			json=json.substring(0,json.length()-1);
		}
		if(page.getTotalPages()==page.getCurrentPage()){
			json+="||0";
		}else{
			json+="||1";
		}
		try {
			res.setHeader("ContentType", "text/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(json);
			res.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
