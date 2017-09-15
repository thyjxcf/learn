/* 
 * @(#)ImportDataTemplateService.java    Created on Aug 10, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.leadin.dataimport.core.ImportObjectNode;
import net.zdsoft.leadin.dataimport.entity.ImportDataTemplate;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 10, 2010 11:08:08 AM $
 */
public interface ImportDataTemplateService {
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
     * 根据单位获取本单位及上级单位的自定义字段
     * 
     * @param initFile
     * @param objectName
     * @param unitId 单位
     * @return
     */
    public Map<String, ImportObjectNode> findfieldCustomMap(String initFile, String objectName,
            String unitId);
}
