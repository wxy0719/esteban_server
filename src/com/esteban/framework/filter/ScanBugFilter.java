package com.esteban.framework.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ScanBugFilter implements Filter {

    public static void main(String[] args) {

    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            request.setCharacterEncoding("UTF-8");
            if (request instanceof HttpServletRequest) {
                HttpServletRequest req = (HttpServletRequest) request;
                HttpServletResponse resp = (HttpServletResponse) response;
                for (String urlIgnore : urlIgnores) {
                    String url = req.getServletPath();
                    if (url.equals(urlIgnore)) {
                        chain.doFilter(request, response);
                        return;
                    }
                }
                String paramStr = isVailed2(req);
                boolean warn = false;
                String msg = "";
                if (null != paramStr && paramStr.length() > 0) {
                    if (isVailed(paramStr) || isSQLInjectionV(paramStr) || isVailedCookie(req)) {
                        msg = "<script language='JavaScript'>alert(\"请求字符中有非法字符串,请检查！\");history.back();</script>";
                        warn = true;
                    }
                }
                if (isVailedWSDL(req)) {
                    msg = "\"禁止的请求！\"";
                    warn = true;
                }
                if (warn) {
                    resp.setCharacterEncoding("GBK");
                    resp.setContentType("text/html; charset=GBK");
                    resp.getWriter().println(msg);
                    return;
                }

            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean isVailedCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
                if (null != cookies[i] && null != cookies[i].getValue() && isSQLInjection(cookies[i].getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean isVailedWSDL(HttpServletRequest request) {
        if (!wsdlCheck)
            return false;
        String[] ipStrings = ipArray.split("\\|");
        if (null != ipStrings) {
            for (int i = 0; i < ipStrings.length; i++) {
                if (request.getRemoteAddr().equals(ipStrings[i])) {
                    return false;
                }
            }
        }
        Map map = request.getParameterMap();
        Set keSet = map.entrySet();
        for (Iterator itr = keSet.iterator(); itr.hasNext();) {
            Map.Entry me = (Map.Entry) itr.next();
            Object ok = me.getKey();
            Object ov = me.getValue();
            if (ok instanceof String)
                if (ok.toString().equalsIgnoreCase("wsdl"))
                    return true;
            if (ov instanceof String)
                if (ov.toString().equalsIgnoreCase("wsdl"))
                    return true;
        }
        return false;
    }

    boolean isSQLInjectionV(String paramStr) {
        if (!sqlCheck)
            return false;
        return isSQLInjection(paramStr);
    }

    boolean isSQLInjection(String paramStr) {
        String inj_str = "execute immediate|truncate|union|drop|'|";
        String inj_stra[] = inj_str.split("\\|");
        for (int i = 0; i < inj_stra.length; i++) {
            if (paramStr.contains(inj_stra[i])) {
                return true;
            }
        }
        return false;
    }

    String isVailed2(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        Map map = request.getParameterMap();
        Set keSet = map.entrySet();
        for (Iterator itr = keSet.iterator(); itr.hasNext();) {
            Map.Entry me = (Map.Entry) itr.next();
            Object ok = me.getKey();
            Object ov = me.getValue();
            String[] value = new String[1];
            if (ov instanceof String[]) {
                value = (String[]) ov;
            } else {
                value[0] = ov.toString();
            }

            for (int k = 0; k < value.length; k++) {
                if (null != value[k] && !"".equals(value[k]))
                    sb.append(value[k]).append("&");
            }
        }
        return sb.toString().toLowerCase();
    }

    boolean isVailed(String paramStr) {
        if (!xssCheck)
            return false;
        String[] params = paramStr.split("&");
        int count = params.length;
        for (int i = 0; i < count; i++) {
            if (params[i].contains("<") || params[i].contains(">") || params[i].contains("'") || params[i].contains("#") || params[i].contains("%0d%0a") || params[i].contains("%0a%0d")
                    || params[i].contains("\r\n") || params[i].contains("\n\r") || params[i].contains("%20") || params[i].contains("\"")) {
                return true;
            }
        }
        return false;
    }

    private String ipArray;
    private boolean sqlCheck;
    private boolean wsdlCheck;
    private boolean xssCheck;
    private String[] urlIgnores;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.ipArray = filterConfig.getInitParameter("webServicesClientIPArray");
        this.sqlCheck = "true".equalsIgnoreCase(filterConfig.getInitParameter("sqlCheck"));
        this.wsdlCheck = "true".equalsIgnoreCase(filterConfig.getInitParameter("wsdlCheck"));
        this.xssCheck = "true".equalsIgnoreCase(filterConfig.getInitParameter("xssCheck"));
        this.urlIgnores = filterConfig.getInitParameter("urlIgnores").split(",");
    }
}