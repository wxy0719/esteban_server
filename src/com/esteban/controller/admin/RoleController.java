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
import com.esteban.model.Rights;
import com.esteban.model.RightsType;
import com.esteban.model.RightsTypeExample;
import com.esteban.model.Role;
import com.esteban.model.RoleExample;
import com.esteban.service.admin.IRightsLogic;
import com.esteban.service.admin.IRightsTypeLogic;
import com.esteban.service.admin.IRoleLogic;

@Controller
@RequestMapping("/admin/role")
public class RoleController {

	@Resource
	private IRoleLogic roleLogic;

	@Resource
	private IRightsLogic rightsLogic;

	@Resource
	private IRightsTypeLogic rightsTypeLogic;

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/listRole")
	public String list(){
		return "admin/role/list_role";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listRoleJSON")
	public void getRoleTable(HttpServletRequest req,HttpServletResponse res,String name,String status){
		Page webPage=WebUtils.getUIPage(req);
		Role role=new Role();
		role.setName(name);
		role.setStatus(status);
		List<Role> list=roleLogic.listByPage(role, webPage);
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
		List<RightsType> listRightsType=rightsTypeLogic.listByPage(null, page);
		model.addAttribute("listRights",listRights);
		model.addAttribute("listRightsType",listRightsType);
		return "admin/role/add_role";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toEdit")
	public String toEdit(String id,Model model,HttpServletRequest request){
		RoleExample roEmp=new RoleExample();
		roEmp.or().andIdEqualTo(id);
		Role role=roleLogic.detailFirstWithBlob(roEmp);

		Page page=WebUtils.getPage(request);
		page.setShowAll(true);
		Rights r=new Rights();
		r.setStatus("1");
		List<Rights> listRights=rightsLogic.listByPage(r, page);
		List<RightsType> listRightsType=rightsTypeLogic.listByPage(null, page);
		model.addAttribute("role",role);
		model.addAttribute("listRights",listRights);
		model.addAttribute("listRightsType",listRightsType);
		return "admin/role/edit_role";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/add")
	public void add(HttpServletRequest req,HttpServletResponse res,Role role){
		String info="添加成功";
		role.setId(UUID.getUUID());
		boolean flag=roleLogic.add(role);
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
	public void edit(HttpServletRequest req,HttpServletResponse res,Role role){
		String info="修改成功";
		RoleExample roEmp=new RoleExample();
		roEmp.or().andIdEqualTo(role.getId());
		boolean flag=roleLogic.modify(role,roEmp);
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
		RoleExample roEmp=new RoleExample();
		roEmp.or().andIdEqualTo(id);
		boolean flag=roleLogic.delete(roEmp);
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
