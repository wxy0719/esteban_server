package com.esteban.business.service.newsManagement.impl;

import com.esteban.business.dao.AdsDao;
import com.esteban.business.model.Ads;
import com.esteban.business.model.AdsExample;
import com.esteban.business.service.newsManagement.IAdsLogic;
import com.esteban.core.framework.utils.UUID;
import com.esteban.core.framework.utils.WebUtils;
import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdsLogic extends BaseServiceImpl<Ads,AdsExample> implements IAdsLogic {

	@Resource
	private AdsDao adsDao;

	@Override
	public IDao getDao() {
		return adsDao;
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
        ads.setCreateOper("");
        boolean flag=false;
        flag=insert(ads);
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
        flag=update(ads,emp);
        if("修改成功".equals(info)&&!flag){
        	info="修改失败";
        }
		return info;
	}

}
