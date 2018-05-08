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
import com.esteban.framework.utils.MD5;
import com.esteban.framework.utils.UUID;
import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.Oper;
import com.esteban.model.OperExample;
import com.esteban.model.Rights;
import com.esteban.model.RightsType;
import com.esteban.model.Role;
import com.esteban.service.admin.IOperLogic;
import com.esteban.service.admin.IRightsLogic;
import com.esteban.service.admin.IRightsTypeLogic;
import com.esteban.service.admin.IRoleLogic;

@Controller
@RequestMapping("/admin/oper")
public class OperController {

	@Resource
	private IOperLogic operLogic;

	@Resource
	private IRightsLogic rightsLogic;

	@Resource
	private IRoleLogic roleLogic;

	@Resource
	private IRightsTypeLogic rightsTypeLogic;

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/listOper")
	public String list(){
		return "admin/oper/list_oper";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listOperJSON")
	public void getOperTable(HttpServletRequest req,HttpServletResponse res,String name,String status){
		Page webPage=WebUtils.getUIPage(req);
		Oper oper=new Oper();
		oper.setName(name);
		oper.setStatus(status);
		List<Oper> list=operLogic.listByPage(oper, webPage);
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
		return "admin/oper/add_oper";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toEdit")
	public String toEdit(String id,Model model,HttpServletRequest request){
		OperExample opEmp=new OperExample();
		opEmp.or().andIdEqualTo(id);
		Oper oper=operLogic.detailFirstWithBlob(opEmp);

		Page page=WebUtils.getPage(request);
		page.setShowAll(true);
		Rights r=new Rights();
		r.setStatus("1");
		List<Rights> listRights=rightsLogic.listByPage(r, page);
		Role role=new Role();
		role.setStatus("1");
		List<Role> listRole=roleLogic.listByPage(role, page);
		List<RightsType> listRightsType=rightsTypeLogic.listByPage(null, page);
		model.addAttribute("oper",oper);
		model.addAttribute("listRights",listRights);
		model.addAttribute("listRole",listRole);
		model.addAttribute("listRightsType",listRightsType);
		return "admin/oper/edit_oper";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/add")
	public void add(HttpServletRequest req,HttpServletResponse res,Oper oper){
		String info="添加成功";
		oper.setId(UUID.getUUID());
		oper.setCreateOper(WebUtils.getOper(req).getId());
		oper.setPasswd(MD5.MD5Encode("123456"));
		boolean flag=operLogic.add(oper);
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
	public void edit(HttpServletRequest req,HttpServletResponse res,Oper oper){
		String info="修改成功";
		OperExample opEmp=new OperExample();
		opEmp.or().andIdEqualTo(oper.getId());
		boolean flag=operLogic.modify(oper,opEmp);
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
		OperExample opEmp=new OperExample();
		opEmp.or().andIdEqualTo(id);
		boolean flag=operLogic.delete(opEmp);
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
