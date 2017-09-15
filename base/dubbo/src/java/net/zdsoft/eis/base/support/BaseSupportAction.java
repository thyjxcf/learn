/* 
 * @(#)BaseSupportAction.java    Created on Feb 25, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.support;

import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.frame.action.BaseAction;

/**
 * 后台管理中涉及到base包下的初始化数据action
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Feb 25, 2011 2:15:18 PM $
 */
public class BaseSupportAction extends BaseAction {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7653502765206858951L;
    
    private SystemDeployService systemDeployService;

    public void setSystemDeployService(SystemDeployService systemDeployService) {
        this.systemDeployService = systemDeployService;
    }

    public String execute() throws Exception {
        return SUCCESS;
    }

    public void initPassportClient() {
        systemDeployService.initPassportClient();
    }
}
