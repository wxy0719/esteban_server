package com.esteban.core.system.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by CPR269 on 2018/5/22.
 */
@Controller("")
public class InterfaceAdapter {

    private static final Logger log = Logger.getLogger(InterfaceAdapter.class);

    /**
     * 判断数据是否符合格式，并包含必要的数据 【data，adapterNo，time，validator，token】
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/interfaceAdapter")
    @ResponseBody
    public Object handleInterface(HttpServletRequest request, HttpServletResponse response){
        String data= request.getParameter("data");
        String adapterNo= request.getParameter("adapterNo");
        String time= request.getParameter("time");
        String validator= request.getParameter("validator");
        String token= request.getParameter("token");


        return null;
    }
}
