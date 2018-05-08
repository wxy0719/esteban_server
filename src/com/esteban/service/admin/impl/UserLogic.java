package com.esteban.service.admin.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.esteban.dao.UserDao;
import com.esteban.framework.utils.MD5;
import com.esteban.framework.utils.Page;
import com.esteban.framework.utils.UUID;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.User;
import com.esteban.model.UserExample;
import com.esteban.service.admin.IConfigLogic;
import com.esteban.service.admin.IUserLogic;

@Service
public class UserLogic implements IUserLogic {

	@Resource
	private UserDao userDao;
	
	@Resource
	private IConfigLogic configLogic;
	
	
	public List<User> listByPage(User t, Page page) {
		return userDao.listByPage(t, page);
	}

	
	public List<User> detail(UserExample emp) {
		if(emp!=null){
			return userDao.selectByExample(emp);
		}
		return null;
	}
	
	
	public List<User> detailWithBlob(UserExample emp) {
		if(emp!=null){
			return userDao.selectByExampleWithBLOBs(emp);
		}
		return null;
	}
	
	public User detailFirst(UserExample emp){
		User u=null;
		List<User> list_=detail(emp);
		if(list_!=null&&list_.size()>0){
			u=list_.get(0);
		}
		return u;
	}
	
	
	public User detailFirstWithBlob(UserExample emp){
		User u=null;
		List<User> list_=detailWithBlob(emp);
		if(list_!=null&&list_.size()>0){
			u=list_.get(0);
		}
		return u;
	}

	
	public boolean add(User t) {
		int result=0;
		if(t!=null){
			result=userDao.insert(t);
		}
		return result>0;
	}

	
	public boolean modifyAll(User t,UserExample emp) {
		int result=0;
		if(t!=null){
			result=userDao.updateByExample(t, emp);
		}
		return result>0;
	}
	
	
	public boolean modify(User t,UserExample emp) {
		int result=0;
		if(t!=null){
			result=userDao.updateByExampleSelective(t, emp);
		}
		return result>0;
	}

	
	public boolean delete(UserExample emp) {
		int result=0;
		if(emp!=null){
			result=userDao.deleteByExample(emp);
		}
		return result>0;
	}

	
	public String addUser(HttpServletRequest req,User user) {
		String info="添加成功";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		
		String id=UUID.getUUID();
		
		String savePath=req.getSession().getServletContext().getRealPath("/")+WebUtils.getConfigValByName("userImgPath");
		File saveForder=new File(savePath);
		if(!saveForder.exists()){
			saveForder.mkdirs();
		}
		
		String[] ext={"jpg","png","gif"};
		List<String> extMap=Arrays.asList(ext);
		
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        if(file!=null&&file.getSize()!=0){
	        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
	        user.setImg(id+"."+suffix.toLowerCase());
	        if (!extMap.contains(suffix.toLowerCase())) {
	            return "类型错误";
	        }
			File uploadFile = new File(saveForder+"/"+id+"."+suffix.toLowerCase());
			try {
				uploadFile.createNewFile();
				FileCopyUtils.copy(file.getBytes(), uploadFile);
			} catch (IOException e) {
				info="上传图片失败";
				e.printStackTrace();
			}
        }
		
		user.setId(id);
		user.setPasswd(MD5.MD5Encode("123456"));
		
		boolean flag=add(user);
		if(!flag&&"添加成功".equals(info)){
			info="添加失败";
		}
		return info;
	}

	
	public boolean deleteUser(String id,HttpServletRequest req) {
		String savePath=req.getSession().getServletContext().getRealPath("/")+WebUtils.getConfigValByName("userImgPath");
		UserExample userEmp=new UserExample();
		userEmp.or().andIdEqualTo(id);
		User user=detail(userEmp).get(0);
		boolean flag=true;
		if(!StringUtils.isBlank(user.getImg())){
			File file=new File(savePath+"/"+user.getImg());
			if(file.exists()){
				flag=file.delete();
			}
		}
		if(flag){
			flag=delete(userEmp);
		}
		return flag;
	}

	
	public String editUser(HttpServletRequest req,User user) {
		String info="修改成功";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		
		String savePath=req.getSession().getServletContext().getRealPath("/")+WebUtils.getConfigValByName("userImgPath");
		
		File saveForder=new File(savePath);
		if(!saveForder.exists()){
			saveForder.mkdirs();
		}
		
		String[] ext={"jpg","png","gif"};
		List<String> extMap=Arrays.asList(ext);
		
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        
        if(file!=null&&file.getSize()!=0){
	        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
	        user.setImg(user.getId()+"."+suffix.toLowerCase());
	        if (!extMap.contains(suffix.toLowerCase())) {
	            return "类型错误";
	        }
			File uploadFile = new File(saveForder+"/"+user.getId()+"."+suffix.toLowerCase());
			try {
				uploadFile.createNewFile();
				FileCopyUtils.copy(file.getBytes(), uploadFile);
			} catch (IOException e) {
				info="上传图片失败";
				e.printStackTrace();
			}
        }
		
		boolean flag=false;
		
		UserExample userEmp=new UserExample();
		userEmp.or().andIdEqualTo(user.getId());
		flag=modify(user,userEmp);
		if(!flag&&"修改成功".equals(info)){
			info="修改失败";
		}
		return info;
	}

}
