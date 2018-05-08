package com.esteban.service.admin;

import javax.servlet.http.HttpServletRequest;

import com.esteban.model.User;
import com.esteban.model.UserExample;
import com.esteban.service.base.BaseService;

public interface IUserLogic extends BaseService<User, UserExample>{

	String addUser(HttpServletRequest req,User user);

	boolean deleteUser(String id,HttpServletRequest req);

	String editUser(HttpServletRequest req,User user);

}
