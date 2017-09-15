/* 
 * @(#)DataImportable.java    Created on Aug 4, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.action;

import java.util.List;

import net.zdsoft.leadin.dataimport.param.DataImportPageParam;
import net.zdsoft.leadin.dataimport.param.DataImportViewParam;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 4, 2010 5:55:36 PM $
 */
public interface DataImportable {
    /**
     * 取业务参数列表
     * 
     * @return
     */
    public List<String[]> getParamsList();

    /**
     * 取公共页面参数
     * @return
     */
    public DataImportPageParam getPageParam();

    /**
     * 取导入、下载模板等用到的参数
     * @return
     */
    public DataImportViewParam getViewParam();
}
