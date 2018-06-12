package com.esteban.core.system.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by CPR269 on 2018/5/9.
 */
public interface ILoginLogic {

    public Object login(HttpServletRequest request, HttpServletResponse response);

    public Object refreshToken(HttpServletRequest request, HttpServletResponse response);

}
