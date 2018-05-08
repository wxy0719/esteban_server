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
import com.esteban.model.Villa;
import com.esteban.model.VillaExample;
import com.esteban.service.admin.IVillaLogic;

@Controller
@RequestMapping("/admin/villa")
public class VillaController {

	@Resource
	private IVillaLogic villaLogic;

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/listVilla")
	public String listVilla(HttpServletRequest req,Model model){

		return "admin/villa/list_villa";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/listVillaJSON")
	public void listVillaJSON(HttpServletRequest req,HttpServletResponse res,Model model,String title){
		Page webPage=WebUtils.getUIPage(req);
		Villa villa=new Villa();
		villa.setTitle(title);
		List<Villa> list=villaLogic.listByPage(villa, webPage);
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
		return "admin/villa/add_villa";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toEdit")
	public String toEdit(Model model,HttpServletRequest request,String id){
		VillaExample viEmp=new VillaExample();
		viEmp.or().andIdEqualTo(id);
		Villa villa=villaLogic.detailFirst(viEmp);


		model.addAttribute("villa", villa);
		return "admin/villa/edit_villa";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/add")
	public String add(HttpServletRequest req,HttpServletResponse res,Villa villa,Model model){
		String info="添加成功";
		villa.setId(UUID.getUUID());
		villa.setCreateuser(WebUtils.getOper(req).getId());
		String flag=villaLogic.addVilla(villa,req);
		if(!info.equals(flag)){
			info=flag;
		}
		model.addAttribute("msg", info);
		return "admin/villa/ifm";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/edit")
	public String edit(HttpServletRequest req,HttpServletResponse res,Villa villa,Model model){
		String info="修改成功";
		String flag=villaLogic.modifyVilla(villa,req);
		if(!info.equals(flag)){
			info=flag;
		}
		model.addAttribute("msg", info);
		return "admin/villa/ifm";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/del")
	public void del(HttpServletRequest req,HttpServletResponse res,String id){
		String info="删除成功";
		VillaExample viEmp=new VillaExample();
		viEmp.or().andIdEqualTo(id);
		boolean flag=villaLogic.delete(viEmp);
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
