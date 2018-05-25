package com.esteban.business.service.newsManagement.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.esteban.business.dao.ArticleDao;
import com.esteban.core.framework.utils.Page;
import com.esteban.core.framework.utils.UUID;
import com.esteban.core.framework.utils.WebUtils;
import com.esteban.business.model.Article;
import com.esteban.business.model.ArticleExample;
import com.esteban.business.service.newsManagement.IArticleLogic;

@Service
public class ArticleLogic extends BaseServiceImpl<Article,ArticleExample> implements IArticleLogic{

	@Resource
	private ArticleDao articleDao;

	@Override
	public IDao getDao() {
		return articleDao;
	}

	public Map<String,String> uploadImg(HttpServletRequest req) {
		Map<String,String> map=new HashMap<String, String>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		
		String id=UUID.getUUID();
		String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() +  req.getContextPath();
		String savePath=req.getSession().getServletContext().getRealPath("/")+WebUtils.getConfigValByName("articleImg");
		File saveForder=new File(savePath);
		if(!saveForder.exists()){
			saveForder.mkdirs();
		}
		
		String[] ext={"jpg","png","gif"};
		List<String> extMap=Arrays.asList(ext);
		
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        if(file!=null&&file.getSize()!=0){
	        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
	        String name = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
	        if (!extMap.contains(suffix.toLowerCase())) {
	        	map.put("state","exceedTypeError");
	        }
			File uploadFile = new File(saveForder+"/"+id+"."+suffix.toLowerCase());
			try {
				uploadFile.createNewFile();
				FileCopyUtils.copy(file.getBytes(), uploadFile);
				map.put("state","SUCCESS");
				map.put("url",basePath+"/"+WebUtils.getConfigValByName("articleImg")+"/"+id+"."+suffix.toLowerCase());
				map.put("title","");
				map.put("original",name);
			} catch (IOException e) {
				map.put("state","loadError");
				e.printStackTrace();
			}
        }
		return map;
	}
	
	public Map<String, String> uploadFile(HttpServletRequest req) {
		Map<String,String> map=new HashMap<String, String>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		
		String id=UUID.getUUID();
		String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() +  req.getContextPath();
		String savePath=req.getSession().getServletContext().getRealPath("/")+WebUtils.getConfigValByName("articleFile");
		File saveForder=new File(savePath);
		if(!saveForder.exists()){
			saveForder.mkdirs();
		}
		
		String[] ext={"png", "jpg", "jpeg", "gif", "bmp", "txt", "word", "xls", "ppt", "zip", "rar"};
		List<String> extMap=Arrays.asList(ext);
		
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        if(file!=null&&file.getSize()!=0){
	        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
	        String name = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
	        if (!extMap.contains(suffix.toLowerCase())) {
	        	map.put("state","exceedTypeError");
	        }
			File uploadFile = new File(savePath+"/"+id+"."+suffix.toLowerCase());
			try {
				uploadFile.createNewFile();
				FileCopyUtils.copy(file.getBytes(), uploadFile);
				map.put("state","SUCCESS");
				map.put("url",basePath+"/"+WebUtils.getConfigValByName("articleFile")+"/"+id+"."+suffix.toLowerCase());
				map.put("original",name);
			} catch (IOException e) {
				map.put("state","loadError");
				e.printStackTrace();
			}
        }
		return map;
	}

	public Map<String, String> uploadVideo(HttpServletRequest req) {
		Map<String,String> map=new HashMap<String, String>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		
		String id=UUID.getUUID();
		String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() +  req.getContextPath();
		String savePath=req.getSession().getServletContext().getRealPath("/")+WebUtils.getConfigValByName("articleVideo");
		File saveForder=new File(savePath);
		if(!saveForder.exists()){
			saveForder.mkdirs();
		}
		
		String[] ext={"jpg","png","gif"};
		List<String> extMap=Arrays.asList(ext);
		
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        if(file!=null&&file.getSize()!=0){
	        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
	        String name = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
	        if (!extMap.contains(suffix.toLowerCase())) {
	        	map.put("state","exceedTypeError");
	        }
			File uploadFile = new File(saveForder+"/"+id+"."+suffix.toLowerCase());
			try {
				uploadFile.createNewFile();
				FileCopyUtils.copy(file.getBytes(), uploadFile);
				map.put("state","SUCCESS");
				map.put("url",basePath+"/"+WebUtils.getConfigValByName("articleVideo")+"/"+id+"."+suffix.toLowerCase());
				map.put("title","");
				map.put("original",name);
			} catch (IOException e) {
				map.put("state","loadError");
				e.printStackTrace();
			}
        }
		return map;
	}

	public List<String> listImgs(HttpServletRequest req) {
		List<String> list=new ArrayList<String>();
		String filePath=req.getSession().getServletContext().getRealPath("/")+WebUtils.getConfigValByName("articleImg");
		String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() +  req.getContextPath();
		
		File file=new File(filePath);
		if(file.exists()){
			File[] fs = file.listFiles();
			for (int i = 0; i < fs.length; i++) {
				list.add(basePath+"/"+WebUtils.getConfigValByName("articleImg")+"/"+fs[i].getName());
				if (!fs[i].isDirectory()) {
				}
			}
		}
		return list;
	}
	
	public List<String> listFiles(HttpServletRequest req) {
		List<String> list=new ArrayList<String>();
		String filePath=req.getSession().getServletContext().getRealPath("/")+WebUtils.getConfigValByName("articleFile");
		String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() +  req.getContextPath();
		
		File file=new File(filePath);
		if(file.exists()){
			File[] fs = file.listFiles();
			for (int i = 0; i < fs.length; i++) {
				list.add(basePath+"/"+WebUtils.getConfigValByName("articleFile")+"/"+fs[i].getName());
				if (!fs[i].isDirectory()) {
				}
			}
		}
		return list;
	}

	public List<Map<String, Object>> queryAllByPage(Article art, Page webPage) {
		return articleDao.queryAllByPage(art, webPage);
	}

}
