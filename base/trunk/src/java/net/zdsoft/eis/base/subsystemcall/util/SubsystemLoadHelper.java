/* 
 * @(#)SubsystemLoadHelper.java    Created on Sep 14, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.subsystemcall.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.zdsoft.eis.base.subsystemcall.service.SubsystemCallService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 14, 2010 10:04:02 AM $
 */
public class SubsystemLoadHelper {
    private static List<SubsystemCallService> subsystemCallServices = new CopyOnWriteArrayList<SubsystemCallService>();

    public static List<SubsystemCallService> getSubsystemCallServices() {
        return subsystemCallServices;
    }

    public static void registerSubsystemCallService(SubsystemCallService service) {
        subsystemCallServices.add(service);
    }
}
