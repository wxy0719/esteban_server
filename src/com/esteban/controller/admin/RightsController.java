package com.esteban.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import com.esteban.model.RightsExample;
import com.esteban.model.RightsType;
import com.esteban.service.admin.IRightsLogic;
import com.esteban.service.admin.IRightsTypeLogic;

@Controller
@RequestMapping("/admin/rights")
public class RightsController {
	@Resource
	private IRightsLogic rightsLogic;

	@Resource
	private IRightsTypeLogic rightsTypeLogic;

	@Login(value = WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/listRights")
	public String list() {
		return "admin/rights/list_rights";
	}

	@Login(value = WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listRightsJSON")
	public void getRoleTable(HttpServletRequest req, HttpServletResponse res,
							 String name, String status) {
		Page webPage = WebUtils.getUIPage(req);
		Rights rights = new Rights();
		rights.setName(name);
		rights.setStatus(status);
		List<Map<String,Object>> list = rightsLogic.listAllInfo(rights, webPage);
		String html = WebUtils.getMapJSON(list, webPage.getTotalRows());
		JSONObject obj = JSONObject.fromObject(html);
		try {
			res.setHeader("ContentType", "text/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(obj);
			res.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Login(value = WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toAdd")
	public String toAdd(HttpServletRequest req,Model model) {
		Page page=WebUtils.getPage(req);
		List<RightsType> list=rightsTypeLogic.listByPage(null, page);
		model.addAttribute("listRightsType", list);
		return "admin/rights/add_rights";
	}

	@Login(value = WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toEdit")
	public String toEdit(String id, Model model,HttpServletRequest req) {
		RightsExample riEmp=new RightsExample();
		riEmp.or().andIdEqualTo(id);
		Rights rights = rightsLogic.detailFirst(riEmp);

		Page page=WebUtils.getPage(req);
		List<RightsType> list=rightsTypeLogic.listByPage(null, page);
		model.addAttribute("listRightsType", list);
		model.addAttribute("rights", rights);
		return "admin/rights/edit_rights";
	}

	@Login(value = WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/add")
	public void add(HttpServletRequest req, HttpServletResponse res, Rights rights) {
		String info = "添加成功";
		rights.setId(UUID.getUUID());
		boolean flag = rightsLogic.add(rights);
		if (!flag) {
			info = "添加失败";
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

	@Login(value = WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/edit")
	public void edit(HttpServletRequest req, HttpServletResponse res, Rights rights) {
		String info = "修改成功";
		RightsExample riEmp=new RightsExample();
		riEmp.or().andIdEqualTo(rights.getId());
		boolean flag = rightsLogic.modify(rights,riEmp);
		if (!flag) {
			info = "修改失败";
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

	@Login(value = WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/del")
	public void del(HttpServletRequest req, HttpServletResponse res, String id) {
		String info = "删除成功";
		RightsExample riEmp=new RightsExample();
		riEmp.or().andIdEqualTo(id);
		boolean flag = rightsLogic.delete(riEmp);
		if (!flag) {
			info = "删除失败";
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
