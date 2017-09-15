/* 
 * @(#)AbstractSubsystemCallService.java    Created on Sep 13, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.subsystemcall.service.impl;

import net.zdsoft.eis.base.subsystemcall.service.SubsystemCallService;
import net.zdsoft.eis.base.subsystemcall.util.SubsystemLoadHelper;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 13, 2010 3:23:23 PM $
 */
public abstract class AbstractSubsystemCallService implements SubsystemCallService {
    public AbstractSubsystemCallService() {
        SubsystemLoadHelper.registerSubsystemCallService(this);
    }

}
