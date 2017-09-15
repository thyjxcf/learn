/* 
 * @(#)EisContextLoader.java    Created on Mar 21, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.keelcnet.config;

import javax.servlet.ServletContext;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Mar 21, 2011 11:02:21 AM $
 */
public class EisContextLoader extends ContextLoader {
    /**
     * 自定义配置位置参数：定时器
     */
    private static final String CUSTOM_SCHEDULER_CONFIG_LOCATION_PARAM = "customsShedulerContextConfigLocation";
    /**
     * 自定义配置位置参数：定时器
     */
    private static final String CUSTOM_DUBBO_CONFIG_LOCATION_PARAM = "customsDubboContextConfigLocation";

    @Override
    protected void customizeContext(ServletContext servletContext,
            ConfigurableWebApplicationContext applicationContext) {

        String[] locations = applicationContext.getConfigLocations();

        // 自定义调度配置参数
        String schedulerConfigLocation = getSchedulerConfigLocation(servletContext);
        if (StringUtils.isNotEmpty(schedulerConfigLocation)) {
            // 将参数加到configLocations
            locations = (String[])ArrayUtils.add(locations, schedulerConfigLocation);
        }
        String dubboConfigLocation = getDubboConfigLocation(servletContext);
        if (StringUtils.isNotEmpty(dubboConfigLocation)) {
            // 将参数加到configLocations
            locations = (String[])ArrayUtils.add(locations, dubboConfigLocation);
        }

        applicationContext.setConfigLocations(locations);
        super.customizeContext(servletContext, applicationContext);
    }

    /**
     * 自定义调度配置参数-定时器
     * 
     * @param servletContext
     * @return
     */
    private String getSchedulerConfigLocation(ServletContext servletContext) {
        String location = null;

        String start = System.getProperty("eis.scheduler.start");
        if (StringUtils.isBlank(start)) {
            start = "false";
        }
        if (Boolean.parseBoolean(start)) {
            location = servletContext.getInitParameter(CUSTOM_SCHEDULER_CONFIG_LOCATION_PARAM);
        } else {
        	System.out.println("========classpath*:conf/spring/scheduler/*.xml	下的定时器和触发器没有开启===========");
        }
        return location;
    }
    
    /**
     * 自定义调度配置参数-dubbo
     * 
     * @param servletContext
     * @return
     */
    private String getDubboConfigLocation(ServletContext servletContext) {
        String location = null;

        String start = System.getProperty("eis.dubbo.open");
        if (StringUtils.isBlank(start)) {
            start = "false";
        }
        if (Boolean.parseBoolean(start)) {
            System.out.println("========dubbo 连接开启，开始加载xml配置===========");
        	location = servletContext.getInitParameter(CUSTOM_DUBBO_CONFIG_LOCATION_PARAM);
        } else {
        	System.out.println("========dubbo 连接没有开启===========");
        }
        return location;
    }
}
