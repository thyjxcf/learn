/* 
 * @(#)ObserverRegister.java    Created on Sep 14, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.observe;

import java.util.Observer;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 14, 2010 2:58:34 PM $
 */
public abstract class ObserverRegister implements Observer, ObserverRegisterable {

    /**
     * 注册观察者
     */
    public ObserverRegister() {
        ObserverHelper.addObserver(fetchKey(), this);
    }

}
