package com.esteban.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esteban.framework.annotation.Login;
import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.UUID;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.RightsType;
import com.esteban.model.RightsTypeExample;
import com.esteban.service.admin.IRightsTypeLogic;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/rightsType")
public class RightsTypeController {

	@Resource
	private IRightsTypeLogic rightsTypeLogic;

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/listRightsType")
	public String list(){
		return "admin/rightsType/list_rightsType";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listRightsTypeJSON")
	public void getRoleTable(HttpServletRequest req,HttpServletResponse res,String name,String status){
		Page webPage=WebUtils.getUIPage(req);
		RightsType rightstype=new RightsType();
		rightstype.setName(name);
		List<RightsType> list=rightsTypeLogic.listByPage(rightstype, webPage);
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
		return "admin/rightsType/add_rightsType";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toEdit")
	public String toEdit(String id,Model model,HttpServletRequest request){
		RightsTypeExample riEmp=new RightsTypeExample();
		riEmp.or().andIdEqualTo(id);
		RightsType rightstype=rightsTypeLogic.detailFirst(riEmp);

		model.addAttribute("rightsType",rightstype);
		return "admin/rightsType/edit_rightsType";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/add")
	public void add(HttpServletRequest req,HttpServletResponse res,RightsType rightstype){
		String info="添加成功";
		rightstype.setId(UUID.getUUID());
		boolean flag=rightsTypeLogic.add(rightstype);
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
	public void edit(HttpServletRequest req,HttpServletResponse res,RightsType rightstype){
		String info="修改成功";
		RightsTypeExample riEmp=new RightsTypeExample();
		riEmp.or().andIdEqualTo(rightstype.getId());
		boolean flag=rightsTypeLogic.modify(rightstype,riEmp);
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
		RightsTypeExample riEmp=new RightsTypeExample();
		riEmp.or().andIdEqualTo(id);
		boolean flag=rightsTypeLogic.delete(riEmp);
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
