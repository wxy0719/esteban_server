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

import com.esteban.dao.WenkuDao;
import com.esteban.framework.utils.Office2PDF;
import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.Pdf2swf;
import com.esteban.framework.utils.UUID;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.Wenku;
import com.esteban.model.WenkuExample;
import com.esteban.service.admin.IWenKuLogic;

@Service
public class WenkuLogic implements IWenKuLogic {

	@Resource
	private WenkuDao WenkuDao;
	
	
	public List<Wenku> listByPage(Wenku t, Page page) {
		return WenkuDao.listByPage(t, page);
	}

	
	public List<Wenku> detail(WenkuExample emp) {
		if(emp!=null){
			return WenkuDao.selectByExample(emp);
		}
		return null;
	}
	
	
	public List<Wenku> detailWithBlob(WenkuExample emp) {
		if(emp!=null){
			return WenkuDao.selectByExampleWithBLOBs(emp);
		}
		return null;
	}
	
	public Wenku detailFirst(WenkuExample emp){
		Wenku wk=null;
		List<Wenku> list_=detail(emp);
		if(list_!=null&&list_.size()>0){
			wk=list_.get(0);
		}
		return wk;
	}
	
	
	public Wenku detailFirstWithBlob(WenkuExample emp){
		Wenku wk=null;
		List<Wenku> list_=detailWithBlob(emp);
		if(list_!=null&&list_.size()>0){
			wk=list_.get(0);
		}
		return wk;
	}

	
	public boolean add(Wenku t) {
		int result=0;
		if(t!=null){
			result=WenkuDao.insert(t);
		}
		return result>0;
	}

	
	public boolean modifyAll(Wenku t,WenkuExample emp) {
		int result=0;
		if(t!=null){
			result=WenkuDao.updateByExample(t,emp);
		}
		return result>0;
	}
	
	
	public boolean modify(Wenku t,WenkuExample emp) {
		int result=0;
		if(t!=null){
			result=WenkuDao.updateByExampleSelective(t,emp);
		}
		return result>0;
	}

	
	public boolean delete(WenkuExample emp) {
		int result=0;
		if(emp!=null){
			result=WenkuDao.deleteByExample(emp);
		}
		return result>0;
	}

	
	public String addWenku(Wenku Wenku, HttpServletRequest req) {
		String info="添加成功";
		String[] ext={"ppt","doc","docx","xls","jpg","png","txt","pdf","gif"};
		List<String> extList=new ArrayList<String>(Arrays.asList(ext));
		String id=UUID.getUUID();
		Wenku.setId(id);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        String ctxPath = req.getSession().getServletContext().getRealPath("/")+ WebUtils.getConfigValByName("DocSavePath");
        
        if(file!=null&&file.getSize()!=0){
        	String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        	if(!extList.contains(suffix.toLowerCase())){
        		info="类型错误";
        		return info;
        	}
        	if("pdf".equals(suffix)){
        		ctxPath=req.getSession().getServletContext().getRealPath("/")+ WebUtils.getConfigValByName("PdfSavePath");
        	}
        	File dirPath = new File(ctxPath);
        	if (!dirPath.exists()) {
        		dirPath.mkdirs();
        	}
        	File uploadFile = new File(ctxPath +"/"+ id+"."+suffix.toLowerCase());
        	try {
        		FileCopyUtils.copy(file.getBytes(), uploadFile);
        	} catch (IOException e) {
        		info="上传文档失败";
        		e.printStackTrace();
        	}
        	
        	if(!"上传文档失败".equals(info)){
        		convertOffeicToPdfThenSwf(ctxPath+"/"+id+"."+suffix.toLowerCase(),req.getSession().getServletContext().getRealPath("/"),id);
        		Wenku.setDocpath(WebUtils.getConfigValByName("SwfSavePath")+"/"+id+".swf");
        	}

        }
        Wenku.setCreateuser(WebUtils.getOper(req).getId());
        boolean flag=false;
        flag=add(Wenku);
        if("添加成功".equals(info)&&!flag){
        	info="添加失败";
        }
		return info;
	}
	
	
	public String modifyWenku(Wenku Wenku, HttpServletRequest req) {
		String info="修改成功";
		String[] ext={"ppt","doc","docx","xls","jpg","png","txt","pdf","gif"};
		List<String> extList=new ArrayList<String>(Arrays.asList(ext));
		String id=Wenku.getId();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        String ctxPath = req.getSession().getServletContext().getRealPath("/")+ WebUtils.getConfigValByName("DocSavePath");
        
        if(file!=null&&file.getSize()!=0){
        	String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        	if(!extList.contains(suffix.toLowerCase())){
        		info="类型错误";
        		return info;
        	}
        	if("pdf".equals(suffix)){
        		ctxPath=req.getSession().getServletContext().getRealPath("/")+ WebUtils.getConfigValByName("PdfSavePath");
        	}
        	File dirPath = new File(ctxPath);
        	if (!dirPath.exists()) {
        		dirPath.mkdirs();
        	}
        	File uploadFile = new File(ctxPath +"/"+ id+"."+suffix.toLowerCase());
        	try {
        		FileCopyUtils.copy(file.getBytes(), uploadFile);
        	} catch (IOException e) {
        		info="上传文档失败";
        		e.printStackTrace();
        	}
        	
        	if(!"上传文档失败".equals(info)){
        		convertOffeicToPdfThenSwf(ctxPath+"/"+id+"."+suffix.toLowerCase(),req.getSession().getServletContext().getRealPath("/"),id);
        		Wenku.setDocpath(WebUtils.getConfigValByName("SwfSavePath")+"/"+id+".swf");
        	}

        }
        boolean flag=false;
        WenkuExample wenKuEmp=new WenkuExample();
        wenKuEmp.or().andIdEqualTo(Wenku.getId());
        
        flag=modify(Wenku,wenKuEmp);
        if("修改成功".equals(info)&&!flag){
        	info="修改失败";
        }
		return info;
	}
	
	public boolean convertOffeicToPdfThenSwf(String filePath,String convertPath,String fileName){
		Office2PDF od=new Office2PDF();
		boolean flag=false;
		File file=new File(convertPath);
		if(!file.exists()){
			file.mkdirs();
		}
		String outPutPdfPath=convertPath+WebUtils.getConfigValByName("PdfSavePath")+"/"+fileName+"."+od.OFFICE_TO_PDF;
		flag=od.openOfficeToPDF(filePath,outPutPdfPath);
		
		Pdf2swf ps=new Pdf2swf(WebUtils.getConfigValByName("SwfToolsPath"));
		String outPutSwfPath=convertPath+WebUtils.getConfigValByName("SwfSavePath")+"/"+fileName+"."+ps.TYPE_SWF;
		flag=ps.convertFileToSwf(outPutPdfPath, outPutSwfPath);
		return flag;
	}

	
	public List<String> listFiles(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

}
