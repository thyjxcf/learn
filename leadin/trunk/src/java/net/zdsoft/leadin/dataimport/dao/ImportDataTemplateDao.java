/* 
 * @(#)ImportDataTemplateDao.java    Created on Aug 10, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.dao;

import java.util.List;

import net.zdsoft.leadin.dataimport.entity.ImportDataTemplate;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 10, 2010 10:56:56 AM $
 */
public interface ImportDataTemplateDao {
    /**
     * 保存模板
     * 
     * @param initFile 配置文件
     * @param objectName 类型
     * @param unitId 单位
     * @param templates
     */
    public void saveTemplates(String initFile, String objectName, String unitId,
            List<ImportDataTemplate> templates);

    /**
     * 获取模板
     * 
     * @param initFile
     * @param objectName
     * @param unitIds
     * @return
     */
    public List<ImportDataTemplate> findTemplates(String initFile, String objectName,
            String... unitIds);

}
