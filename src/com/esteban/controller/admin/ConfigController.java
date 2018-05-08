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
import com.esteban.model.Config;
import com.esteban.model.ConfigExample;
import com.esteban.service.admin.IConfigLogic;

@Controller
@RequestMapping("/admin/config")
public class ConfigController {

	@Resource
	private IConfigLogic configLogic;

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/listConfig")
	public String list(){
		return "admin/config/list_config";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listConfigJSON")
	public void getConfigTable(HttpServletRequest req,HttpServletResponse res,String name){
		Page webPage=WebUtils.getUIPage(req);
		Config config=new Config();
		config.setName(name);
		List<Config> list=configLogic.listByPage(config, webPage);
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
		return "admin/config/add_config";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toEdit")
	public String toEdit(String id,Model model,HttpServletRequest request){
		ConfigExample conEmp=new ConfigExample();
		conEmp.or().andIdEqualTo(id);
		Config config=configLogic.detailFirst(conEmp);

		model.addAttribute("config",config);
		return "admin/config/edit_config";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/add")
	public void add(HttpServletRequest req,HttpServletResponse res,Config config){
		String info="添加成功";
		config.setId(UUID.getUUID());
		boolean flag=configLogic.add(config);
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
	public void edit(HttpServletRequest req,HttpServletResponse res,Config config){
		String info="修改成功";
		ConfigExample conEmp=new ConfigExample();
		conEmp.or().andIdEqualTo(config.getId());
		boolean flag=configLogic.modify(config,conEmp);
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
		ConfigExample conEmp=new ConfigExample();
		conEmp.or().andIdEqualTo(id);
		boolean flag=configLogic.delete(conEmp);
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
