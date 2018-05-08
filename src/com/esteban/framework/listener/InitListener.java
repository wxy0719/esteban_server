package com.esteban.framework.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.esteban.framework.utils.SpringBeanFactory;

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
