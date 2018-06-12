package com.esteban.core.framework.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author esteban
 * @since 2014年5月20日
 */
public class CrossDomainInterceptor implements HandlerInterceptor {
    private static Log log = LogFactory.getLog(CrossDomainInterceptor.class);

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse reponse, Object arg2, Exception arg3) throws Exception {
        reponse.setHeader("Access-Control-Allow-Origin", "*");
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse reponse, Object arg2, ModelAndView arg3) throws Exception {
        reponse.setHeader("Access-Control-Allow-Origin", "*");
    }

    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
        arg1.setHeader("Access-Control-Allow-Origin", "*");
        return true;
    }

}
