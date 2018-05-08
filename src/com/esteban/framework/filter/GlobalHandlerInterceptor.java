package com.esteban.framework.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.esteban.framework.annotation.Login;
import com.esteban.framework.utils.HttpUtils;
import com.esteban.framework.utils.WebUtils;
import com.esteban.model.Oper;
import com.esteban.model.Agent;
import com.esteban.framework.exception.AdminLoginException;
import com.esteban.framework.exception.LoginException;

/**
 * @author esteban
 * @since 2014年5月20日
 */
public class GlobalHandlerInterceptor implements HandlerInterceptor {
    private static Log log = LogFactory.getLog(GlobalHandlerInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod hm = null;
        Login login = null;
        try {
            if (handler instanceof HandlerMethod) {
                hm = (HandlerMethod) handler;
                login = hm.getMethod().getAnnotation(Login.class);
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
                }
            } else if (WebUtils.ADMIN_OPER.equals(login.value())) {
                String path = request.getRequestURL().toString();
                request.getSession().setAttribute("RedirectPath", path);
                Oper oper = WebUtils.getOper(request.getSession());
                if (null == oper || StringUtils.isBlank(oper.getName())) {
                    throw new AdminLoginException("请登陆系统！");
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
