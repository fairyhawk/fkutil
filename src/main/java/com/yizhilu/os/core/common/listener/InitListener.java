package com.yizhilu.os.core.common.listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.yizhilu.os.core.util.PropertyUtil;

/**
 * @ClassName com.yizhilu.os.ssicore.common.InitListener
 * @description
 * @author : qinggang.liu bis@foxmail.com
 * @Create Date : 2014-4-15 下午2:29:08
 */
public class InitListener extends ContextLoaderListener {

    // 读取配置文件类
    public static PropertyUtil propertyUtil = PropertyUtil.getInstance("project");

    public InitListener() {
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            String contextPath = propertyUtil.getProperty("contextPath");
            if (contextPath.indexOf("268xue.com") > 0) {
                servletContextEvent.getServletContext().setAttribute("system.check", "OK");
                super.contextInitialized(servletContextEvent);
            } else {
                System.exit(0);
            }
        } catch (Exception e) {
            System.exit(0);
        }
    }

}