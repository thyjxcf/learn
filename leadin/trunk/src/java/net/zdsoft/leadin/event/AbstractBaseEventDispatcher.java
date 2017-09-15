/* 
 * @(#)BaseEventDispatcherImpl.java    Created on Dec 30, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.event;

import java.util.List;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 30, 2009 1:55:25 PM $
 */
public abstract class AbstractBaseEventDispatcher implements BaseEventDispatcher {
    protected List<String> listenerList;

    public void setListenerList(List<String> listenerList) {
        this.listenerList = listenerList;
    }
}
