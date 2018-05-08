package com.esteban.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esteban.framework.annotation.Login;
import com.esteban.framework.utils.UUID;
import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.Oper;
import com.esteban.model.Rights;
import com.esteban.model.Role;
import com.esteban.model.RoleExample;
import com.esteban.model.MenuTree;
import com.esteban.model.MenuTreeExample;
import com.esteban.service.admin.IOperLogic;
import com.esteban.service.admin.IRightsLogic;
import com.esteban.service.admin.IRoleLogic;
import com.esteban.service.admin.IMenuTreeLogic;

@Controller
@RequestMapping("/admin/tree")
public class TreeMenuController {

	@Resource
	private IMenuTreeLogic treeMenuLogic;

	@Resource
	private IOperLogic operLogic;

	@Resource
	private IRoleLogic roleLogic;

	@Resource
	private IRightsLogic rightsLogic;

	/**
	 * @param request
	 * @param nodeGrade
	 * @param parentNode
	 * @param res
	 * 获取所有的树形菜单
	 */
	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listAllTreeJSON")
	public void listAllTreeJSON(HttpServletRequest request,String nodeGrade,String parentNode,HttpServletResponse res){
		List<MenuTree> listOne=treeMenuLogic.queryTreeMenu(nodeGrade, parentNode,null);
		String str="[";
		str+=getHtmlTHREE(listOne, null);
		str+="]";
		try {
			res.setHeader("ContentType", "text/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param request
	 * @param nodeGrade
	 * @param parentNode
	 * @param res
	 * 获取当前登录用户的树形菜单
	 */
	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/treeJSON")
	public void getTreeJSON(HttpServletRequest request,String nodeGrade,String parentNode,HttpServletResponse res){
		Oper oper=WebUtils.getOper(request);
		List<String> rights=operLogic.getOperRights(oper);
		String str="";
		if(rights!=null){
			str="[";
			List<MenuTree> listOne=treeMenuLogic.queryTreeMenu(nodeGrade, parentNode,rights);
			str+=getHtmlONE(listOne, rights);
			str+="]";
		}
		try {
			res.setHeader("ContentType", "text/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param request
	 * @param nodeGrade
	 * @param parentNode
	 * @param res
	 * 获取指定角色的树形菜单权限
	 */
	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/roleTreeJSON")
	public void getRoleTreeJSON(HttpServletRequest request,String nodeGrade,String parentNode,String roleID,HttpServletResponse res){
		RoleExample roEmp=new RoleExample();
		roEmp.or().andIdEqualTo(roleID);
		Role role=roleLogic.detailFirst(roEmp);
		List<String> rights=role.getListRights();
		List<MenuTree> listOne=treeMenuLogic.queryTreeMenu(nodeGrade, parentNode,null);
		String str="[";
		str+=getHtmlTWO(listOne, rights);
		str+="]";
		try {
			res.setHeader("ContentType", "text/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getHtmlONE(List<MenuTree> listTree,List<String> rights){
		String str="";
		int flag=1;
		for(MenuTree tree:listTree){
			str+="{";
			str+="'id':'"+tree.getId()+"',";
			str+="'text':'"+tree.getName()+"',";
			if("Y".equals(tree.getIsforder())) str+="'state':'closed',";
			if(!StringUtils.isBlank(tree.getUrl())) str+="'url':'"+tree.getUrl()+"',";
			List<MenuTree> listTreeNext = treeMenuLogic.queryTreeMenu(tree.getNodeGrade(), tree.getId(),rights);
			if(listTreeNext!=null&&listTreeNext.size()!=0){
				str+="'children':[";
				str+=getHtmlONE(listTreeNext,rights);
				str+="]";
			}else{
				str=str.substring(0,str.length()-1);
			}
			if(flag!=listTree.size()){
				str+="},";
			}else{
				str+="}";
			}
			flag++;
		}
		return str;
	}

	public String getHtmlTWO(List<MenuTree> listTree,List<String> rights){
		String str="";
		int flag=1;
		for(MenuTree tree:listTree){
			str+="{";
			str+="'id':'"+tree.getId()+"',";
			str+="'text':'"+tree.getName()+"',";
			if("Y".equals(tree.getIsforder())) str+="'state':'closed',";
			if(rights!=null&&rights.contains(tree.getRightNo())) str+="'checked':'true',";
			if(!StringUtils.isBlank(tree.getUrl())) str+="'url':'"+tree.getUrl()+"',";
			List<MenuTree> listTreeNext = treeMenuLogic.queryTreeMenu(tree.getNodeGrade(), tree.getId(),null);
			if(listTreeNext!=null&&listTreeNext.size()!=0){
				str+="'children':[";
				str+=getHtmlTWO(listTreeNext,rights);
				str+="]";
			}else{
				str=str.substring(0,str.length()-1);
			}
			if(flag!=listTree.size()){
				str+="},";
			}else{
				str+="}";
			}
			flag++;
		}
		return str;
	}

	public String getHtmlTHREE(List<MenuTree> listTree,List<String> rights){
		String str="";
		int flag=1;
		for(MenuTree tree:listTree){
			str+="{";
			str+="'id':'"+tree.getId()+"',";
			str+="'text':'"+tree.getName()+"',";
			if("Y".equals(tree.getIsforder())) str+="'state':'closed',";
			if(!StringUtils.isBlank(tree.getUrl())) str+="'url':'"+tree.getUrl()+"',";
			List<MenuTree> listTreeNext = treeMenuLogic.queryTreeMenu(tree.getNodeGrade(), tree.getId(),null);
			if(listTreeNext!=null&&listTreeNext.size()!=0){
				str+="'children':[";
				str+=getHtmlTHREE(listTreeNext,null);
				str+="]";
			}else{
				str=str.substring(0,str.length()-1);
			}
			if(flag!=listTree.size()){
				str+="},";
			}else{
				str+="}";
			}
			flag++;
		}
		return str;
	}



	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/listMenu")
	public String list(HttpServletRequest request,Model model){
		Page page=WebUtils.getPage(request);
		page.setShowAll(true);
		List<Rights> listRights=rightsLogic.listByPage(null, page);
		model.addAttribute("listRights",listRights);
		return "admin/menu/list_menu";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toEdit")
	public String toEdit(HttpServletRequest request,Model model,String id){
		MenuTreeExample mtEmp=new MenuTreeExample();
		mtEmp.or().andIdEqualTo(id);
		MenuTree tm=treeMenuLogic.detailFirst(mtEmp);

		Page page=WebUtils.getPage(request);
		page.setShowAll(true);
		Rights r=new Rights();
		r.setStatus("1");
		List<Rights> listRights=rightsLogic.listByPage(r, page);
		model.addAttribute("listRights",listRights);
		model.addAttribute("tm",tm);
		return "admin/menu/edit_menu";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/toAdd")
	public String toAdd(HttpServletRequest request,Model model,String id){
		Page page=WebUtils.getPage(request);
		page.setShowAll(true);
		Rights r=new Rights();
		r.setStatus("1");
		List<Rights> listRights=rightsLogic.listByPage(r, page);
		model.addAttribute("listRights",listRights);
		model.addAttribute("pid",id);
		return "admin/menu/add_menu";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/add")
	public void add(HttpServletRequest req,HttpServletResponse res,MenuTree tree,String pid){
		MenuTree tm=null;
		MenuTreeExample mtEmp=new MenuTreeExample();
		mtEmp.or().andIdEqualTo(pid);
		List<MenuTree> listMenu=treeMenuLogic.detail(mtEmp);
		if(listMenu!=null&&listMenu.size()>0){
			tm=listMenu.get(0);
		}

		String info="添加成功";
		tree.setId(UUID.getUUID());
		tree.setNodeGrade((Integer.parseInt(tm.getNodeGrade())+1)+"");
		tree.setParentNode(tm.getId());
		tree.setIsforder("N");
		tm.setIsforder("Y");
		boolean pFlag=treeMenuLogic.modify(tm,mtEmp);
		boolean flag=treeMenuLogic.add(tree);
		if(!flag||!pFlag){
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
	public void edit(HttpServletRequest req,HttpServletResponse res,MenuTree tree){
		String info="修改成功";
		MenuTreeExample mtEmp=new MenuTreeExample();
		mtEmp.or().andIdEqualTo(tree.getId());
		boolean flag=treeMenuLogic.modify(tree,mtEmp);
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
		boolean pFlag=true;
		MenuTree tm=null;
		MenuTreeExample mtEmp=new MenuTreeExample();
		mtEmp.or().andIdEqualTo(id);
		List<MenuTree> listMenu=treeMenuLogic.detail(mtEmp);
		if(listMenu!=null&&listMenu.size()>0){
			tm=listMenu.get(0);
		}

		mtEmp.clear();
		mtEmp.or().andParentNodeEqualTo(tm.getParentNode());
		List<MenuTree> list=treeMenuLogic.detail(mtEmp);
		if(list==null||list.size()==0||list.size()==1){
			MenuTree ptm=null;
			mtEmp.clear();
			mtEmp.or().andParentNodeEqualTo(tm.getParentNode());
			List<MenuTree> list_=treeMenuLogic.detail(mtEmp);
			if(list_!=null&&list_.size()>0){
				ptm=list_.get(0);
			}
			ptm.setIsforder("N");
			mtEmp.clear();
			mtEmp.or().andIdEqualTo(ptm.getId());
			pFlag=treeMenuLogic.modify(ptm,mtEmp);
		}
		mtEmp.clear();
		mtEmp.or().andIdEqualTo(id);
		boolean flag=treeMenuLogic.delete(mtEmp);
		if(!flag||!pFlag){
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
