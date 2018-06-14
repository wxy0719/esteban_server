package com.esteban.core.framework.listener;

import com.alibaba.fastjson.JSON;
import com.esteban.core.framework.utils.RedisUtils;
import com.esteban.core.framework.utils.SpringBeanFactory;
import com.esteban.core.system.model.Config;
import com.esteban.core.system.model.ConfigExample;
import com.esteban.core.system.model.MenuTree;
import com.esteban.core.system.model.MenuTreeExample;
import com.esteban.core.system.service.IConfigLogic;
import com.esteban.core.system.service.IMenuTreeLogic;
import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;
import java.util.Map;

/**
 * 系统启动初始化
 *
 */
public class InitListener extends HttpServlet implements ServletContextListener, HttpSessionListener {
    protected static Logger log = Logger.getLogger(InitListener.class);
    private static final long serialVersionUID = 5725274398751969680L;

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
     */
    public synchronized void sessionCreated(HttpSessionEvent arg0) {
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
     */
    public synchronized void sessionDestroyed(HttpSessionEvent arg0) {
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // 把Spring的BeanFactory传入静态工厂里
            SpringBeanFactory.setBf(WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext()));

            RedisUtils redisUtils = (RedisUtils) SpringBeanFactory.getBean("redisUtils");

            // 将菜单数据写入redis
            IMenuTreeLogic menuTreeLogic=(IMenuTreeLogic)SpringBeanFactory.getBean("menuTreeLogic");
            List<Map<String,String>> listParent = menuTreeLogic.getDistinctParentId(false);

            if(listParent!=null && listParent.size()>0){
                for(Map<String,String> p:listParent){
                    MenuTreeExample menuEmp=new MenuTreeExample();
                    menuEmp.createCriteria().andParentNodeEqualTo(p.get("parentId"));
                    List<MenuTree> menuTreeList=menuTreeLogic.detail(menuEmp);
                    String menuStr = JSON.toJSONString(menuTreeList);
                    redisUtils.set(p.get("parentId"),menuStr);
                }
            }

            // 将config数据写入redis
            IConfigLogic configLogic=(IConfigLogic)SpringBeanFactory.getBean("configLogic");
            ConfigExample conEmp=new ConfigExample();
            List<Config> configList=configLogic.detail(conEmp);
            if(configList!=null&&configList.size()>0){
                for(Config c:configList){
                    String configStr = JSON.toJSONString(c);
                    redisUtils.set(c.getName(),configStr);
                }
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
