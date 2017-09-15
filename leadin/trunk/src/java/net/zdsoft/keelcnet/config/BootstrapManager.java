/* 
 * @(#)BaseConfig.java    Created on 2006-12-14
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keelcnet/config/BootstrapManager.java,v 1.6 2007/09/20 08:21:09 liangxiao Exp $
 */
package net.zdsoft.keelcnet.config;

import java.io.File;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.helper.Nothing;

public class BootstrapManager {

//    private static final String CNET_HOME_CONSTANT = "${cnetHome}";
//    private static final String CNET_DOMAIN_PROP = "cnet.domain";
//    private static final String WEBAPP_CONTEXT_PATH_KEY = "cnet.webapp.context.path";
    
    private static boolean devModel = false;
    static {
        ResourceBundle bundle = ResourceBundle.getBundle("struts");
        if(bundle != null) {
            devModel = BooleanUtils.toBoolean(bundle.getString("struts.devMode"));
        }
    }

    // properties
    private static final String WW_ENCODING = "webwork.i18n.encoding";
    private static final String STORE_FOLDER = "storeFolder";

    // store folder
    private static final String DEFAULT_STORE_FOLDER = "store";

    // script folder
    private static final String DEFAULT_SCRIPT_FOLDER = "script";
    
    // Properties是同步的
    private static Map<Object, Object> properties = new ConcurrentHashMap<Object, Object>();

    private static String storeHome;
    
    private static String scriptHome;
    

    static {
        properties.putAll(FileUtils.readProperties(new Nothing().getClass()
                .getClassLoader().getResourceAsStream("keelcnet.properties")));
    }

    private BootstrapManager() {
    }

    private static HttpServletRequest getRequest() {
        HttpServletRequest req = ServletActionContext.getRequest();
        return req;
    }
    
    /**
     * 返回带contextPath的基础路径
     * 
     * @return
     */
    public static String getBaseUrl() {
        String basePath = getDomainName() + getWebAppContextPath();
        return StringUtils.trimToEmpty(basePath);        
    }

    public static String getDomainName() {
        // return (String) properties.get(CNET_DOMAIN_PROP);

        HttpServletRequest req = getRequest();
        String domain = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();
        
        return StringUtils.trimToEmpty(domain);
    }

    public static String getWebAppContextPath() {
        // return (String) properties.get(WEBAPP_CONTEXT_PATH_KEY);
        HttpServletRequest req = getRequest();
        return req.getContextPath();
    }

    public static String getEncoding() {
        return (String) properties.get(WW_ENCODING);
    }

//    public static String getUploadFolder() {
//        String uploadFolder = (String) properties.get(WW_UPLOADFOLDER);
//        return uploadFolder == null ? null : uploadFolder.replaceAll(
//                CNET_HOME_CONSTANT, getStoreHome());
//    }
    
    /**
     * Store目录也是通过数据库配置，此方法不准确，不建议使用
     * @deprecated 
     * @return
     */
    public static String getStoreHome() {
        return storeHome;
    }

    protected static void setStoreHome(ServletContext servletContext) {
        if (properties.get("storeFolder") == null) {
            setStoreHomeDefault(servletContext);
        }
        else {
            storeHome = (String) properties.get("storeFolder");
        }
        System.out.println("提示：文件存放目录 storeHome = " + storeHome);
    }
    
    /**
     * 将store路径设置为默认的与应用同级的目录
     * @param servletContext
     */
    public static void setStoreHomeDefault(ServletContext servletContext) {
        String webHome = servletContext.getRealPath("/");
        int index = webHome.lastIndexOf(File.separator, webHome.length() - 2);
        storeHome = webHome.substring(0, index + 1) + DEFAULT_STORE_FOLDER;
    }
    
    public static void setStoreHome(String storeHome) {
        BootstrapManager.storeHome = storeHome;
    }

    public static String getScriptHome() {
		return scriptHome;
	}

    protected static void setScriptHome(ServletContext servletContext) {
        if (properties.get("scriptFolder") == null) {
            setScriptHomeDefault(servletContext);
        }
        else {
        	scriptHome = (String) properties.get("scriptFolder");
        }
        System.out.println("提示：脚本存放目录 scriptHome = " + scriptHome);
    }
    
    /**
     * 将script路径设置为默认的与应用同级的目录
     * @param servletContext
     */
	public static void setScriptHomeDefault(ServletContext servletContext) {
		String webHome = servletContext.getRealPath("/");
        int index = webHome.lastIndexOf(File.separator, webHome.length() - 2);
        scriptHome = webHome.substring(0, index + 1) + DEFAULT_SCRIPT_FOLDER;
	}
	public static String getStoreFolder() {
        return (String) properties.get(STORE_FOLDER);
    }
	
//    public static String getString(String key) {
//        return (String) properties.get(key);
//    }
//
//    public static Object getProperty(String key) {
//        return properties.get(key);
//    }
//
//    public static void setProperty(String key, Object value) {
//        properties.put(key, value);
//    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // String webHome = "E:\\Tomcat5.5\\webapps\\zdsoft\\";
        // int index = webHome.lastIndexOf(File.separator, webHome.length() -
        // 2);
        // System.out.println(webHome.substring(0, index + 1) + STORE_FOLDER);
    }

    public static boolean isDevModel() {
        return devModel;
    }

}
