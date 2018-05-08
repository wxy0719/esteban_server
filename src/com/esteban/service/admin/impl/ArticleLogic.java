package com.esteban.service.admin.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.esteban.dao.ArticleDao;
import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.UUID;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.Ads;
import com.esteban.model.AdsExample;
import com.esteban.model.Article;
import com.esteban.model.ArticleExample;
import com.esteban.service.admin.IArticleLogic;

@Service
public class ArticleLogic implements IArticleLogic{

	@Resource
	private ArticleDao articleDao;
	
	public List<Article> listByPage(Article t, Page page) {
		List<Article> list= articleDao.listByPage(t, page);
		if(list!=null&&list.size()!=0){
			for(int i=0;i<list.size();i++){
				Article a=list.get(i);
				a.setContent("");
			}
		}
		return list;
	}
	
	public List<Article> detail(ArticleExample emp) {
		if(emp!=null){
			return articleDao.selectByExample(emp);
		}
		return null;
	}
	
	
	public List<Article> detailWithBlob(ArticleExample emp) {
		if(emp!=null){
			return articleDao.selectByExampleWithBLOBs(emp);
		}
		return null;
	}
	
	public Article detailFirst(ArticleExample emp){
		Article art=null;
		List<Article> listArticle=detail(emp);
		if(listArticle!=null&&listArticle.size()>0){
			art=listArticle.get(0);
		}
		return art;
	}
	
	public Article detailFirstWithBlob(ArticleExample emp){
		Article art=null;
		List<Article> listArticle=detailWithBlob(emp);
		if(listArticle!=null&&listArticle.size()>0){
			art=listArticle.get(0);
		}
		return art;
	}
	
	public boolean add(Article t) {
		int result=0;
		if(t!=null){
			result=articleDao.insert(t);
		}
		return result>0;
	}

	public boolean modifyAll(Article t,ArticleExample emp) {
		int result=0;
		if(t!=null){
			result=articleDao.updateByExample(t, emp);
		}
		return result>0;
	}
	
	public boolean modify(Article t,ArticleExample emp) {
		int result=0;
		if(t!=null){
			result=articleDao.updateByExampleSelective(t, emp);
		}
		return result>0;
	}
	
	public boolean delete(ArticleExample emp) {
		int result=0;
		if(emp!=null){
			result=articleDao.deleteByExample(emp);
		}
		return result>0;
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
