/* 
 * @(#)DeleteDataService.java    Created on Jan 7, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.frame.service;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jan 7, 2010 11:38:47 AM $
 */
public interface DeleteXmlDataService {
    /**
     * 硬删除或软删除与之关联的相关表的数据， 主要用于本身的where条件中有IN参数的情况，故只能单个删除
     * 
     * @param configFile 配置文件名
     * @param arg 参数
     */
    public void delete(String configFile, String arg);

    /**
     * 硬删除或软删除与之关联的相关表的数据
     * 
     * @param configFile
     * @param args 使用in删除
     */
    public void delete(String configFile, String[] args);
}
