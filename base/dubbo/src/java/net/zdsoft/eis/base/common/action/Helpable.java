/* 
 * @(#)Helpable.java    Created on Aug 24, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.action;

import net.zdsoft.eis.base.common.service.BasicModuleService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 24, 2010 4:06:47 PM $
 */
public interface Helpable {
    /**
     * 取模块service
     * 
     * @return
     */
    public BasicModuleService getBasicModuleService();
}
