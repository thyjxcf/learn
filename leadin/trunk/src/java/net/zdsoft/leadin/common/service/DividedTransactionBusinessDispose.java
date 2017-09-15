/* 
 * @(#)DividedTransactionBusinessDispose.java    Created on Sep 25, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.common.service;

import java.util.List;

/**
 * 业务处理
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 25, 2010 10:45:34 AM $
 */
public interface DividedTransactionBusinessDispose<E> {
    /**
     * 保存数据
     * 
     * @param list
     * @return
     */
    public int saveDatas(List<E> list);
}
