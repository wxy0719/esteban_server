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
import com.esteban.model.Ads;
import com.esteban.model.AdsExample;
import com.esteban.service.admin.IAdsLogic;

@Controller
@RequestMapping("/admin/ads")
public class AdsController {

	@Resource
	private IAdsLogic adsLogic;

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/listAds")
	public String list(){
		return "admin/ads/list_ads";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listAdsJSON")
	public void getAdsTable(HttpServletRequest req,HttpServletResponse res,String des){
		Page webPage=WebUtils.getUIPage(req);
		Ads ads=new Ads();
		ads.setDes(des);
		List<Ads> list=adsLogic.listByPage(ads, webPage);
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
		return "admin/ads/add_ads";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toEdit")
	public String toEdit(String id,Model model,HttpServletRequest request){
		AdsExample emp=new AdsExample();
		emp.or().andIdEqualTo(id);
		Ads ads=null;
		List<Ads> list=adsLogic.detail(emp);
		if(list!=null&&list.size()>0){
			ads=list.get(0);
		}
		model.addAttribute("ads",ads);
		return "admin/ads/edit_ads";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/add")
	public String add(HttpServletRequest req,HttpServletResponse res,Ads ads,Model model){
		String info="";
		ads.setId(UUID.getUUID());
		info=adsLogic.addAds(ads,req);
		model.addAttribute("msg", info);
		return "admin/ads/ifm";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/edit")
	public String edit(HttpServletRequest req,HttpServletResponse res,Ads ads,Model model){
		String info="";
		info=adsLogic.editAds(ads,req);
		model.addAttribute("msg", info);
		return "admin/ads/ifm";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/del")
	public void del(HttpServletRequest req,HttpServletResponse res,String id){
		String info="删除成功";
		boolean flag=adsLogic.deleteAds(id,req);
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
