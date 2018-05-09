package com.esteban.core.framework.filter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esteban.core.framework.annotation.NeedLog;
import com.esteban.core.framework.utils.DateOperator;
import com.esteban.core.system.service.IOperLogic;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.esteban.core.framework.annotation.Login;
import com.esteban.core.framework.utils.HttpUtils;
import com.esteban.core.framework.utils.WebUtils;
import com.esteban.core.system.model.Oper;
import com.esteban.business.model.Agent;
import com.esteban.core.framework.exception.AdminLoginException;
import com.esteban.core.framework.exception.LoginException;

/**
 * @author esteban
 * @since 2014年5月20日
 */
public class GlobalHandlerInterceptor implements HandlerInterceptor {
    private static Log log = LogFactory.getLog(GlobalHandlerInterceptor.class);

    @Resource
    private IOperLogic operLogic;


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod hm = null;
        Login login = null;
        NeedLog needLog = null;
        try {
            if (handler instanceof HandlerMethod) {
                hm = (HandlerMethod) handler;
                login = hm.getMethod().getAnnotation(Login.class);
                needLog = hm.getMethod().getAnnotation(NeedLog.class);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        if (null != login) {
            if (WebUtils.AGENT_OPER.equals(login.value())) {
                String path = request.getRequestURL().toString();
                request.getSession().setAttribute("RedirectPath", path);
                Agent agent = WebUtils.getAgent(request.getSession());
                if (null == agent || StringUtils.isBlank(agent.getMobile())) {
                    throw new LoginException("请登陆系统!");
                }else{
                    if(needLog != null){
                        String operateContent="";
                        if(!"".equals(needLog.operateContent())){
                            operateContent=needLog.operateContent();
                        }else{
                            operateContent=path;
                        }
                        log.info(agent.getName()+","+ DateOperator.getNowDate()+",执行了【"+operateContent+"】");
                        operLogic.saveLog(agent.getIdNum(),agent.getName()+","+ DateOperator.getNowDate()+",执行了【"+operateContent+"】",request.getRemoteAddr());
                    }
                }
            } else if (WebUtils.ADMIN_OPER.equals(login.value())) {
                String path = request.getRequestURL().toString();
                request.getSession().setAttribute("RedirectPath", path);
                Oper oper = WebUtils.getOper(request.getSession());
                if (null == oper || StringUtils.isBlank(oper.getName())) {
                    throw new AdminLoginException("请登陆系统！");
                }else{
                    if(needLog != null){
                        String operateContent="";
                        if(!"".equals(needLog.operateContent())){
                            operateContent=needLog.operateContent();
                        }else{
                            operateContent=path;
                        }
                        log.info(oper.getName()+","+ DateOperator.getNowDate()+",执行了【"+operateContent+"】");
                        operLogic.saveLog(oper.getId(),oper.getName()+","+ DateOperator.getNowDate()+",执行了【"+operateContent+"】",request.getRemoteAddr());
                    }
                }
            }
        }

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (null == modelAndView) {
            return;
        }

        // 系统配置参数
        //        String basePath = HttpUtils.getBasePath(request);
        //        String contextPath = HttpUtils.getContextPath(request);
        //        modelAndView.addObject("basePath", basePath);
        //        modelAndView.addObject("contextPath", contextPath);

        MDC.put("ip", HttpUtils.getIp(request));
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
