package com.esteban.core.system.service.impl;

import com.esteban.core.framework.utils.MD5;
import com.esteban.core.framework.utils.UUID;
import com.esteban.core.framework.utils.WebUtils;
import com.esteban.core.system.dao.UserDao;
import com.esteban.core.system.dao.base.IDao;
import com.esteban.core.system.model.User;
import com.esteban.core.system.model.UserExample;
import com.esteban.core.system.service.IConfigLogic;
import com.esteban.core.system.service.IUserLogic;
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
import java.util.Arrays;
import java.util.List;

@Service
public class UserLogic extends BaseServiceImpl<User,UserExample> implements IUserLogic {

	@Resource
	private UserDao userDao;
	
	@Resource
	private IConfigLogic configLogic;


	@Override
	public IDao getDao() {
		return userDao;
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
		user.setPasswd(MD5.stringMD5("123456"));
		
		boolean flag=insert(user);
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
		flag=update(user,userEmp);
		if(!flag&&"修改成功".equals(info)){
			info="修改失败";
		}
		return info;
	}

}
