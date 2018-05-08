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
import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.Rights;
import com.esteban.model.RightsType;
import com.esteban.model.Role;
import com.esteban.model.User;
import com.esteban.model.UserExample;
import com.esteban.service.admin.IRightsLogic;
import com.esteban.service.admin.IRightsTypeLogic;
import com.esteban.service.admin.IRoleLogic;
import com.esteban.service.admin.IUserLogic;

@Controller("adminUserController")
@RequestMapping("/admin/user")
public class UserController {

	@Resource
	private IUserLogic userLogic;

	@Resource
	private IRightsLogic rightsLogic;

	@Resource
	private IRightsTypeLogic rightsTypeLogic;

	@Resource
	private IRoleLogic roleLogic;

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/listUser")
	public String listUser(){

		return "admin/user/list_user";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listUserJSON")
	public void getUserTable(HttpServletRequest req,HttpServletResponse res,String name,String status){
		Page webPage=WebUtils.getUIPage(req);
		User user=new User();
		user.setName(name);
		user.setStatus(status);
		List<User> list=userLogic.listByPage(user, webPage);
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
		Page page=WebUtils.getPage(request);
		page.setShowAll(true);
		Rights r=new Rights();
		r.setStatus("1");
		List<Rights> listRights=rightsLogic.listByPage(r, page);
		Role role=new Role();
		role.setStatus("1");
		List<Role> listRole=roleLogic.listByPage(role, page);
		List<RightsType> listRightsType=rightsTypeLogic.listByPage(null, page);
		model.addAttribute("listRights",listRights);
		model.addAttribute("listRole",listRole);
		model.addAttribute("listRightsType",listRightsType);
		return "admin/user/add_user";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toEdit")
	public String toEdit(String id,Model model,HttpServletRequest request){
		UserExample userEmp=new UserExample();
		userEmp.or().andIdEqualTo(id);
		User user=userLogic.detailFirstWithBlob(userEmp);

		Page page=WebUtils.getPage(request);
		page.setShowAll(true);
		Rights r=new Rights();
		r.setStatus("1");
		List<Rights> listRights=rightsLogic.listByPage(r, page);
		Role role=new Role();
		role.setStatus("1");
		List<Role> listRole=roleLogic.listByPage(role, page);
		List<RightsType> listRightsType=rightsTypeLogic.listByPage(null, page);
		model.addAttribute("listRole",listRole);
		model.addAttribute("listRights",listRights);
		model.addAttribute("listRightsType",listRightsType);
		model.addAttribute("user",user);
		return "admin/user/edit_user";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/add")
	public String add(HttpServletRequest req,HttpServletResponse res,Model model,User user){
		String info="";
		info=userLogic.addUser(req,user);
		model.addAttribute("msg", info);
		return "admin/user/ifm";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/edit")
	public String edit(HttpServletRequest req,HttpServletResponse res,Model model,User user){
		String info="";
		info=userLogic.editUser(req,user);
		model.addAttribute("msg", info);
		return "admin/user/ifm";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/del")
	public void del(HttpServletRequest req,HttpServletResponse res,String id){
		String info="删除成功";
		boolean flag=userLogic.deleteUser(id,req);
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
