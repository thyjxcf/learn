/* 
 * @(#)SystemInitContextListener.java    Created on Dec 8, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.frame.web;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import net.zdsoft.eis.base.affair.AffairService;
import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.base.common.service.SysOptionService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.deploy.SystemDeployUtils;
import net.zdsoft.eis.base.sync.SyncHelper;
import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.keelcnet.config.ContainerManager;

/**
 * 系统初始化
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 8, 2010 11:01:17 AM $
 */
public class SystemInitContextListener implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(SystemInitContextListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        SysOptionService sysOptionService = (SysOptionService) ContainerManager
                .getComponent("sysOptionService");

        // 设置store目录
        String storeFolder = sysOptionService.getValue(SysOption.FILE_PATH);
        if (StringUtils.isNotBlank(storeFolder)) {
            // 配置文件storeHome只写了/store，前面的内容从数据库中读取
            storeFolder = storeFolder + BootstrapManager.getStoreHome();
            BootstrapManager.setStoreHome(storeFolder);
            System.out.println("提示：根据数据库配置修改文件存放目录 storeHome = " + storeFolder);
        } else {
            // 如果数据库中为空，且配置文件中的store目录为不存在（相对路径），则置为与应用同级的目录
            //目的：配置文件中默认为/store，保持各种环境模式（产品、数字校园、集群等）下打包的一致
            String storeHome = BootstrapManager.getStoreHome();            
            File file = new File(storeHome);
            // System.out.println(file.getAbsolutePath());
            if (!(file.exists()) || !(file.isDirectory()) || "/store".equals(storeHome)) {
                BootstrapManager.setStoreHomeDefault(sce.getServletContext());
                System.out.println("提示： 原文件存放目录 storeHome [" + storeHome+"]不存在，重置为[" + BootstrapManager.getStoreHome()+"]");
            }
        }

        //设置系统内置的公共目录为store
        StorageDirService storageDirService = (StorageDirService) ContainerManager
                .getComponent("storageDirService");
        storageDirService.updatePresetPublicDir(BootstrapManager.getStoreHome());

        // 初始当前发布的子系统
        String appCode = System.getProperty("eis.run.subsystem");
        if (StringUtils.isBlank(appCode)) {
            appCode = sce.getServletContext().getInitParameter(WebUtils.WEB_APP_ROOT_KEY_PARAM);
        }
        log.debug("appCode=(" + WebUtils.WEB_APP_ROOT_KEY_PARAM + "=" + appCode + ")");
        SystemDeployUtils.initCurrentDeployAppCode(appCode);

        // 初始化passport
        passportInit();
        
        //接收mq数据
        SyncHelper.start();
        
        //将系统内置的待办事项的receiver_id(初始化语法时为32个0)，自动置为顶级单位id
        AffairService affairService = (AffairService) ContainerManager
                .getComponent("affairService");
        affairService.updateAffairSystemReceiverId();

    }

    /**
     * passport初始化
     */
    private void passportInit() {
        SystemDeployService systemDeployService = (SystemDeployService) ContainerManager
                .getComponent("systemDeployService");

        systemDeployService.initPassportClient();
    }

}
