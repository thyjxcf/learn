/* 
 * @(#)SystemRunParamAction.java    Created on Jun 3, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.system.frame.action;

import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.system.frame.web.PermissionCheckInterceptor;

/**
 * 系统运行参数
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 3, 2010 10:54:56 AM $
 */
public class SystemRunParamAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    public String execute() throws Exception {
        return super.execute();
    }

    // ================//权限检查时不符合规则(主链接-子链接)时是否跳过=============
    public boolean isPermissionCheckNoRuleSkip() {
        return PermissionCheckInterceptor.isNoRuleSkip();
    }

    public void setPermissionCheckNoRuleSkip(boolean permissionCheckNoRuleSkip) {
        PermissionCheckInterceptor.setNoRuleSkip(permissionCheckNoRuleSkip);
    }

}
