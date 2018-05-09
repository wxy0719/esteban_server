package com.esteban.business.controller.newsManagement;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esteban.core.framework.annotation.Login;
import com.esteban.core.framework.utils.WebUtils;

@Controller
@RequestMapping("/admin/area")
public class AreaController {
	private static String PATH="/newsManagement/area/";

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/list")
	public String areaList(HttpServletRequest req,Model model,String name){
		model.addAttribute("name", name);
		return PATH+"areaList";
	}
}
