/* 
 * @(#)DataImportListener.java    Created on 2007-3-25
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44CVSROOT/exam/src/net/zdsoft/exam/dataimport/DataImportListener.java,v 1.1 2007/03/26 13:05:00 linqz Exp $
 */
package net.zdsoft.leadin.dataimport.core;

import net.zdsoft.leadin.dataimport.event.DataImportEvent;



public interface DataImportListener {
    /**
     * 导入前做的工作
     *
     * @author linqzh Jan 4, 2008
     * @param event
     * @throws Exception
     */
    public void doBefore(DataImportEvent event) throws Exception;

    /**
     * 导入后做的工作
     *
     * @author linqzh Jan 4, 2008
     * @param event
     * @throws Exception
     */
    public void doAfter(DataImportEvent event) throws Exception;
}