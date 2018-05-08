package com.esteban.controller.front;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.Wenku;
import com.esteban.model.WenkuExample;
import com.esteban.service.admin.IWenKuLogic;

@Controller("frontWenkuController")
@RequestMapping("/front/wenku")
public class WenKuController {

	@Resource
	private IWenKuLogic wenKuLogic;

	@RequestMapping("/{wenkuID}")
	public String detail(HttpServletRequest req,@PathVariable String wenkuID,Model model){
		Wenku wenku=null;
		WenkuExample wkEmp=new WenkuExample();
		wkEmp.or().andIdEqualTo(wenkuID);
		List<Wenku> list_=wenKuLogic.detail(wkEmp);
		if(list_!=null&&list_.size()>0){
			wenku=list_.get(0);
		}

		model.addAttribute("wenku", wenku);
		return "/front/wenku/detail";
	}

	@RequestMapping("/listWenKu")
	public String listWenKu(HttpServletRequest req,Model model){
		Wenku wk=new Wenku();
		Page page=WebUtils.getPage(req);
		List<Wenku> list=wenKuLogic.listByPage(wk, page);
		model.addAttribute("list", list);
		return "/front/wenku/list";
	}
}
