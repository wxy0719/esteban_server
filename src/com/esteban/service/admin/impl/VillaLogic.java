package com.esteban.service.admin.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.esteban.dao.VillaDao;
import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.Villa;
import com.esteban.model.VillaExample;
import com.esteban.service.admin.IVillaLogic;

@Service
public class VillaLogic implements IVillaLogic {

	@Resource
	private VillaDao villaDao;
	
	
	public List<Villa> listByPage(Villa t, Page page) {
		return villaDao.listByPage(t, page);
	}

	
	public List<Villa> detail(VillaExample emp) {
		if(emp!=null){
			return villaDao.selectByExample(emp);
		}
		return null;
	}
	
	
	public List<Villa> detailWithBlob(VillaExample emp) {
		if(emp!=null){
			return villaDao.selectByExampleWithBLOBs(emp);
		}
		return null;
	}
	
	public Villa detailFirst(VillaExample emp){
		Villa v=null;
		List<Villa> list_=detail(emp);
		if(list_!=null&&list_.size()>0){
			v=list_.get(0);
		}
		return v;
	}
	
	
	public Villa detailFirstWithBlob(VillaExample emp){
		Villa v=null;
		List<Villa> list_=detailWithBlob(emp);
		if(list_!=null&&list_.size()>0){
			v=list_.get(0);
		}
		return v;
	}

	
	public boolean add(Villa t) {
		int result=0;
		if(t!=null){
			result=villaDao.insert(t);
		}
		return result>0;
	}

	 
	public boolean modifyAll(Villa t,VillaExample emp) {
		int result=0;
		if(t!=null){
			result=villaDao.updateByExample(t,emp);
		}
		return result>0;
	}
	
	
	public boolean modify(Villa t,VillaExample emp) {
		int result=0;
		if(t!=null){
			result=villaDao.updateByExampleSelective(t,emp);
		}
		return result>0;
	}

	
	public boolean delete(VillaExample emp) {
		int result=0;
		if(emp!=null){
			result=villaDao.deleteByExample(emp);
		}
		return result>0;
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
        flag=add(villa);
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
        flag=modify(villa,vaillEmp);
        if("修改成功".equals(info)&&!flag){
        	info="修改失败";
        }
		return info;
	}

}
