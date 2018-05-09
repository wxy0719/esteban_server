package com.esteban.core.system.service;

import javax.servlet.http.HttpServletRequest;

import com.esteban.core.system.model.User;
import com.esteban.core.system.model.UserExample;
import com.esteban.core.system.service.base.IBaseService;

public interface IUserLogic extends IBaseService<User, UserExample> {

	String addUser(HttpServletRequest req,User user);

	boolean deleteUser(String id,HttpServletRequest req);

	String editUser(HttpServletRequest req,User user);

}
