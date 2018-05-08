package com.esteban.service.admin.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.esteban.dao.AdsDao;
import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.UUID;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.Ads;
import com.esteban.model.AdsExample;
import com.esteban.service.admin.IAdsLogic;

@Service
public class AdsLogic implements IAdsLogic {

	@Resource
	private AdsDao adsDao;
	
	public List<Ads> listByPage(Ads t, Page page) {
		return adsDao.listByPage(t, page);
	}

	public List<Ads> detail(AdsExample adEmp) {
		if(adEmp!=null){
			return adsDao.selectByExample(adEmp);
		}
		return null;
	}
	
	
	public List<Ads> detailWithBlob(AdsExample adEmp) {
		if(adEmp!=null){
			return adsDao.selectByExampleWithBLOBs(adEmp);
		}
		return null;
	}
	
	public Ads detailFirst(AdsExample adEmp){
		Ads ad=null;
		if(adEmp!=null){
			List<Ads> list_= adsDao.selectByExample(adEmp);
			if(list_!=null&&list_.size()>0){
				ad=list_.get(0);
			}
		}
		return ad;
	}
	
	
	public Ads detailFirstWithBlob(AdsExample adEmp){
		Ads ad=null;
		if(adEmp!=null){
			List<Ads> list_= adsDao.selectByExampleWithBLOBs(adEmp);
			if(list_!=null&&list_.size()>0){
				ad=list_.get(0);
			}
		}
		return ad;
	}

	public boolean add(Ads t) {
		int result=0;
		if(t!=null){
			result=adsDao.insert(t);
		}
		return result>0;
	}

	public boolean modify(Ads t,AdsExample emp) {
		int result=0;
		if(t!=null){
			result=adsDao.updateByExampleSelective(t, emp);
		}
		return result>0;
	}
	
	public boolean modifyAll(Ads t,AdsExample emp) {
		int result=0;
		if(t!=null){
			result=adsDao.updateByExample(t, emp);
		}
		return result>0;
	}

	public boolean delete(AdsExample emp) {
		int result=0;
		if(emp!=null){
			result=adsDao.deleteByExample(emp);
		}
		return result>0;
	}

	public String addAds(Ads ads, HttpServletRequest req) {
		String info="添加成功";
		String[] ext={"jpg","gif","png"};
		List<String> extList=new ArrayList<String>(Arrays.asList(ext));
		String id=UUID.getUUID();
		ads.setId(id);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        
        if(file!=null&&file.getSize()!=0){
        	String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        	if(!extList.contains(suffix.toLowerCase())){
        		info="类型错误";
        		return info;
        	}
        	ads.setImgPath(WebUtils.getConfigValByName("ggImgPath")+"/"+id+"."+suffix.toLowerCase());
        	
			String ctxPath = req.getSession().getServletContext().getRealPath("/")+ WebUtils.getConfigValByName("ggImgPath");

			File dirPath = new File(ctxPath);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			File uploadFile = new File(ctxPath +"/"+ id+"."+suffix.toLowerCase());
			try {
				FileCopyUtils.copy(file.getBytes(), uploadFile);
			} catch (IOException e) {
				info="上传图片失败";
				e.printStackTrace();
			}
        }
        ads.setCreateOper(WebUtils.getOper(req).getName());
        boolean flag=false;
        flag=add(ads);
        if("添加成功".equals(info)&&!flag){
        	info="添加失败";
        }
		return info;
	}

	public boolean deleteAds(String id,HttpServletRequest req) {
		String ctxPath = req.getSession().getServletContext().getRealPath("/");
		AdsExample emp=new AdsExample();
		emp.or().andIdEqualTo(id);
		List<Ads> list=detail(emp);
		Ads ad=null;
		if(list!=null&&list.size()>0){
			ad=list.get(0);
		}
		boolean flag=true;
		if(!StringUtils.isBlank(ad.getImgPath())){
			File file=new File(ctxPath+"/"+ad.getImgPath());
			if(file.exists()){
				flag=file.delete();
			}
		}
		if(flag){
			flag=delete(emp);
		}
		return flag;
	}

	public String editAds(Ads ads, HttpServletRequest req) {
		String info="修改成功";
		String[] ext={"jpg","gif","png"};
		List<String> extList=new ArrayList<String>(Arrays.asList(ext));
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        
        if(file!=null&&file.getSize()!=0){
        	String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        	if(!extList.contains(suffix.toLowerCase())){
        		info="类型错误";
        		return info;
        	}
        	ads.setImgPath(WebUtils.getConfigValByName("ggImgPath")+"/"+ads.getId()+"."+suffix.toLowerCase());
        	
			String ctxPath = req.getSession().getServletContext().getRealPath("/")+ WebUtils.getConfigValByName("ggImgPath");

			File dirPath = new File(ctxPath);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			File uploadFile = new File(ctxPath +"/"+ ads.getId()+"."+suffix.toLowerCase());
			try {
				FileCopyUtils.copy(file.getBytes(), uploadFile);
			} catch (IOException e) {
				info="上传图片失败";
				e.printStackTrace();
			}
        }
        boolean flag=false;
        AdsExample emp=new AdsExample();
        emp.or().andIdEqualTo(ads.getId());
        flag=modify(ads,emp);
        if("修改成功".equals(info)&&!flag){
        	info="修改失败";
        }
		return info;
	}

}
