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

import com.esteban.business.dao.WenkuDao;
import com.esteban.core.framework.utils.Office2PDF;
import com.esteban.core.framework.utils.Page;
import com.esteban.core.framework.utils.Pdf2swf;
import com.esteban.core.framework.utils.UUID;
import com.esteban.core.framework.utils.WebUtils;
import com.esteban.business.model.Wenku;
import com.esteban.business.model.WenkuExample;
import com.esteban.business.service.newsManagement.IWenKuLogic;

@Service
public class WenkuLogic extends BaseServiceImpl<Wenku,WenkuExample> implements IWenKuLogic {

	@Resource
	private WenkuDao WenkuDao;


	@Override
	public IDao getDao() {
		return WenkuDao;
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
        flag=insert(Wenku);
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
        
        flag=update(Wenku,wenKuEmp);
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
