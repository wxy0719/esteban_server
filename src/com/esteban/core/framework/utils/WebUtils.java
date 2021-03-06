package com.esteban.core.framework.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.esteban.core.framework.utils.redis.RedisUtils;
import com.esteban.core.system.model.Config;
import com.esteban.core.system.model.LoginLog;
import com.esteban.core.system.model.MenuTree;
import com.esteban.core.system.model.Oper;
import com.esteban.core.system.service.ILoginLogLogic;
import com.esteban.core.system.service.IOperLogic;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author esteban
 * @since 2014年5月23日
 */
public class WebUtils {
    /** 管理平台用户session对象名 */
    public static final String ADMIN_OPER = "beanOper";
    /** 前台用户session对象名 */
    public static final String AGENT_OPER = "webUser";
    public static final String AGENT_ANDROID = "AgentClient";

    public static LoginLog checkTokenIsValid(String token){
        ILoginLogLogic loginLogLogic=(ILoginLogLogic)SpringBeanFactory.getBean("loginLogLogic");
        return loginLogLogic.checkTokenIsValid(token);
    }

    public static Oper getOper(String token){
        Oper oper = null;
        ILoginLogLogic loginLogLogic=(ILoginLogLogic)SpringBeanFactory.getBean("loginLogLogic");
        LoginLog log = loginLogLogic.checkTokenIsValid(token);

        if(log!=null){
            IOperLogic operLogic=(IOperLogic)SpringBeanFactory.getBean("operLogic");
            oper = operLogic.getOperById(log.getUserid());
        }
        return oper;
    }


    public static void toJson(HttpServletResponse response, String data, Object obj) {
        response.setContentType("text/html");
        response.setCharacterEncoding("GBK");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            JSONObject json = new JSONObject();
            if (obj != null) {
                json.accumulate("success", true);
            } else {
                json.accumulate("success", false);
            }
            json.accumulate(data, obj);
            out.println(json.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    /**
     * 得到请求的IP地址
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(ip)) {
            ip = request.getHeader("Host");
        }
        if (StringUtils.isBlank(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isBlank(ip)) {
            ip = "0.0.0.0";
        }
        return ip;
    }

    /**
     * 得到请求的根目录
     *
     * @param request
     * @return
     */
    public static String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        return basePath;
    }

    /**
     * 得到结构目录
     *
     * @param request
     * @return
     */
    public static String getContextPath(HttpServletRequest request) {
        String path = request.getContextPath();
        return path;
    }

    public static Page getPage(HttpServletRequest request) {
        String currentPage = request.getParameter("page.currentPage");
        String showRows = request.getParameter("page.showRows");

        Page page = new Page();
        page.setCurrentPage(NumberUtils.toInt(currentPage, 1));
        page.setShowRows(NumberUtils.toInt(showRows, page.getShowRows()));
        return page;
    }

    public static Page getUIPage(HttpServletRequest request) {
        int pageNum=1;
        int rows=10;
        if(!StringUtils.isBlank(request.getParameter("page"))){
            pageNum=Integer.parseInt(request.getParameter("page"));
        }
        if(!StringUtils.isBlank(request.getParameter("rows"))){
            rows=Integer.parseInt(request.getParameter("rows"));
        }
        Page page = new Page();
        page.setCurrentPage(pageNum);
        page.setShowRows(rows);
        return page;
    }

    public static String getPojo(Object obj) {
        String str="{";
        List<Object> list = new ArrayList<Object>();
        Class clazz = obj.getClass();
        Field[] fields = obj.getClass().getDeclaredFields();//获得属性
        for (Field field : fields) {
            try {
                if(!"serialVersionUID".equals(field.getName())){
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                    Method getMethod = pd.getReadMethod();//获得get方法
                    Object o = getMethod.invoke(obj);//执行get方法返回一个Object
                    if(o==null){
                        str+="'"+field.getName()+"':'',";
                    }else{
                        str+="'"+field.getName()+"':'"+o.toString()+"',";
                    }
                }
            }catch (Exception e) {
            }
        }
        if(str.endsWith(","))str=str.substring(0,str.length()-1);
        str+="},";
        return str;
    }

    public static String getTableJSON(List list,int pageTotal){
        String str="{";
        if(list!=null&&list.size()!=0){
            str+="'total':'"+pageTotal+"',";
            str+="'rows':[";
            for(Object obj:list){
                str+=getPojo(obj);
            }
            if(str.endsWith(","))str=str.substring(0,str.length()-1);
            str+="]";
        }
        str+="}";
        return str;
    }

    public static String getMapJSON(List<Map<String,Object>> list,int pageTotal){
        String str="{";
        if(list!=null&&list.size()!=0){
            str+="'total':'"+pageTotal+"',";
            str+="'rows':[";
            for(Map<String,Object> map:list){
                str+=getMap(map);
            }
            if(str.endsWith(","))str=str.substring(0,str.length()-1);
            str+="]";
        }
        str+="}";
        return str;
    }

    private static String getMap(Map<String,Object> map) {
        String str="{";
        if(map!=null){
            Set<String> key=map.keySet();
            for (Iterator it = key.iterator(); it.hasNext();) {
                String s = (String) it.next();
                str+="'"+s+"':'"+map.get(s)+"',";
            }
            if(str.endsWith(","))str=str.substring(0,str.length()-1);
        }
        str+="},";
        return str;
    }

    public static String getConfigValByName(String name){
        Config con=getConfigByName(name);
        if(con!=null){
            return con.getValue();
        }
        return "";
    }

    public static Config getConfigByName(String name){
        RedisUtils redisUtils = (RedisUtils) SpringBeanFactory.getBean("redisUtils");
        Config con = JSON.parseObject(redisUtils.getString(name),Config.class);
        return con;
    }

    public static List<MenuTree> getMenuByRights(String parentId, List<String> rights) {
        List<MenuTree> list= new ArrayList<>();

        RedisUtils redisUtils = (RedisUtils) SpringBeanFactory.getBean("redisUtils");
        String menuListStr = redisUtils.getString(parentId);
        JSONArray menuListArr = JSON.parseArray(menuListStr);
        for(int i=0;i<menuListArr.size();i++){
            MenuTree menu = menuListArr.getObject(i,MenuTree.class);
            if(rights.contains(menu.getRightNo())){
                list.add(menu);
            }
        }
        Collections.sort(list,new Comparator<MenuTree>() {
            @Override
            public int compare(MenuTree o1, MenuTree o2) {
                int i = Integer.parseInt(o1.getOrder()) - Integer.parseInt(o2.getOrder());
                return i;
            }
        });

        return list;
    }
}
