/* 
 * @(#)Transactable.java    Created on Dec 29, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.affair;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 29, 2010 3:03:00 PM $
 */
public interface Transactable {
    /**
     * 标识符
     * 
     * @return
     */
    public String getIdentifier();

    /**
     * 是否已完成
     * 
     * @return
     */
    public boolean isComplete();

    /**
     * 获得接收者
     * 
     * @return
     */
    public String getReceiverId();
}
