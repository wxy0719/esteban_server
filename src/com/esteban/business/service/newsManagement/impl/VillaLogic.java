package com.esteban.business.service.newsManagement.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.esteban.business.dao.VillaDao;
import com.esteban.core.framework.utils.Page;
import com.esteban.core.framework.utils.WebUtils;
import com.esteban.business.model.Villa;
import com.esteban.business.model.VillaExample;
import com.esteban.business.service.newsManagement.IVillaLogic;

@Service
public class VillaLogic extends BaseServiceImpl<Villa,VillaExample> implements IVillaLogic {

	@Resource
	private VillaDao villaDao;


	@Override
	public IDao getDao() {
		return villaDao;
	}

	public String addVilla(Villa villa, HttpServletRequest req) {
		String info="添加成功";
		String[] ext={"jpg","png","gif"};
		List<String> extList=new ArrayList<String>(Arrays.asList(ext));
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        String ctxPath = req.getSession().getServletContext().getRealPath("/")+ WebUtils.getConfigValByName("VillaSavePath");
        String id=villa.getId();
        
        if(file!=null&&file.getSize()!=0){
        	String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        	if(!extList.contains(suffix.toLowerCase())){
        		info="类型错误";
        		return info;
        	}
        	File dirPath = new File(ctxPath);
        	if (!dirPath.exists()) {
        		dirPath.mkdirs();
        	}
        	File uploadFile = new File(ctxPath +"/"+ id+"."+suffix.toLowerCase());
        	try {
        		FileCopyUtils.copy(file.getBytes(), uploadFile);
        		villa.setPath(WebUtils.getConfigValByName("VillaSavePath")+"/"+ id+"."+suffix.toLowerCase());
        	} catch (IOException e) {
        		info="上传文档失败";
        		e.printStackTrace();
        	}
        }
        boolean flag=false;
        flag=insert(villa);
        if("添加成功".equals(info)&&!flag){
        	info="添加失败";
        }
		return info;
	}

	
	public String modifyVilla(Villa villa, HttpServletRequest req) {
		String info="修改成功";
		String[] ext={"jpg","png","gif"};
		List<String> extList=new ArrayList<String>(Arrays.asList(ext));
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        String ctxPath = req.getSession().getServletContext().getRealPath("/")+ WebUtils.getConfigValByName("VillaSavePath");
        String id=villa.getId();
        
        if(file!=null&&file.getSize()!=0){
        	String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        	if(!extList.contains(suffix.toLowerCase())){
        		info="类型错误";
        		return info;
        	}
        	File dirPath = new File(ctxPath);
        	if (!dirPath.exists()) {
        		dirPath.mkdirs();
        	}
        	File uploadFile = new File(ctxPath +"/"+ id+"."+suffix.toLowerCase());
        	try {
        		FileCopyUtils.copy(file.getBytes(), uploadFile);
        		villa.setPath(WebUtils.getConfigValByName("VillaSavePath")+"/"+ id+"."+suffix.toLowerCase());
        	} catch (IOException e) {
        		info="上传文档失败";
        		e.printStackTrace();
        	}
        }
        boolean flag=false;
        
        VillaExample vaillEmp=new VillaExample();
        vaillEmp.or().andIdEqualTo(villa.getId());
        flag=update(villa,vaillEmp);
        if("修改成功".equals(info)&&!flag){
        	info="修改失败";
        }
		return info;
	}

}
