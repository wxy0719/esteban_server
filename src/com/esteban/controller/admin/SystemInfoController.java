package com.esteban.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esteban.framework.annotation.Login;
import com.esteban.framework.utils.DateOperator;
import com.esteban.framework.utils.SystemUtils;
import com.esteban.framework.utils.WebUtils;

@Controller
@RequestMapping("/admin/sysinfo")
public class SystemInfoController {

	private static List<Object> listMemUsed=new ArrayList<Object>();
	private static List<Object> listMemTime=new ArrayList<Object>();

	@Login(value=WebUtils.ADMIN_OPER)
	@RequestMapping("/ifm/listInfo")
	public String listInfo(HttpServletRequest req,Model model){
		model.addAttribute("JDKVersion", SystemUtils.getJDKVersion());
		model.addAttribute("tomcatVersion", SystemUtils.getTomcatVersion(req));
		model.addAttribute("computerName", SystemUtils.getComputerName());
		model.addAttribute("OSArch", SystemUtils.getOSArch());
		model.addAttribute("OSName", SystemUtils.getOSName());
		model.addAttribute("OSVersion", SystemUtils.getOSVersion());
		try {
			model.addAttribute("memTotal", SystemUtils.getTotalMem());
			model.addAttribute("memLeft", SystemUtils.getFreeMem());
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/sysinfo/list";
	}

	@Login(value=WebUtils.ADMIN_OPER)
	@ResponseBody
	@RequestMapping("/ifm/listSystemInfo")
	public void getSystemInfo(HttpServletRequest req,HttpServletResponse res){
		JSONObject jsonObj = new JSONObject();
		try {
			List<Map<String, Object>> list=SystemUtils.getCpuInfo();
			List<Map<String, Object>> listCpu=new ArrayList<Map<String,Object>>();
			for(int i=0;i<list.size();i++){
				JSONObject jsonCPU = new JSONObject();
				jsonCPU.put("totalMHz", list.get(i).get("totalMHz"));
				jsonCPU.put("cpuType", list.get(i).get("cpuType"));
				jsonCPU.put("used", list.get(i).get("used"));
				jsonCPU.put("free", list.get(i).get("free"));
				jsonCPU.put("userUsed", list.get(i).get("userUsed"));
				jsonCPU.put("sysUsed", list.get(i).get("sysUsed"));
				listCpu.add(jsonCPU);
			}
			JSONArray jsonArray = JSONArray.fromObject(listCpu);
			jsonObj.put("listCpu", jsonArray);
		} catch (SigarException e) {
			e.printStackTrace();
		}
		try {
			Map<String,Object> map=SystemUtils.getMemoryInfo();
			jsonObj.put("totalMem", map.get("totalMem"));

			if(listMemUsed.size()==12){
				listMemUsed.remove(0);
			}
			listMemUsed.add(map.get("usedMem"));

			if(listMemTime.size()==12){
				listMemTime.remove(0);
			}
			listMemTime.add(DateOperator.format(new Date(), "mm:ss"));

			JSONArray jsonArrayUsed= JSONArray.fromObject(listMemUsed);
			JSONArray jsonArrayTime = JSONArray.fromObject(listMemTime);
			jsonObj.put("listMemUsed", jsonArrayUsed);
			jsonObj.put("listMemTime", jsonArrayTime);
		} catch (SigarException e) {
			e.printStackTrace();
		}
		try {
			res.setHeader("ContentType", "text/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(jsonObj);
			res.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
