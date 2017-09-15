/* 
 * @(#)SystemDefaultBusinessRoleObserverRegister.java    Created on Sep 14, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.observe;

import net.zdsoft.leadin.observe.ObserverRegister;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 14, 2010 3:01:03 PM $
 */
public abstract class SystemDefaultBusinessRoleObserverRegister extends ObserverRegister {

    public int fetchKey() {
        return ObserveKey.BUSINESSROLE.getValue();
    }
}
