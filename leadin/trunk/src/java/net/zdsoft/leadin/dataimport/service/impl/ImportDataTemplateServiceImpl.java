/* 
 * @(#)ImportDataTemplateServiceImpl.java    Created on Aug 10, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.leadin.dataimport.core.ImportObjectNode;
import net.zdsoft.leadin.dataimport.dao.ImportDataTemplateDao;
import net.zdsoft.leadin.dataimport.entity.ImportDataTemplate;
import net.zdsoft.leadin.dataimport.service.ImportDataTemplateService;
import net.zdsoft.leadin.dataimport.subsystemcall.BaseSubsystemService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 10, 2010 11:08:32 AM $
 */
public class ImportDataTemplateServiceImpl implements ImportDataTemplateService {
    private ImportDataTemplateDao leadinImportDataTemplateDao;
    private BaseSubsystemService baseSubsystemService;

    public void setLeadinImportDataTemplateDao(ImportDataTemplateDao leadinImportDataTemplateDao) {
        this.leadinImportDataTemplateDao = leadinImportDataTemplateDao;
    }

    public void setBaseSubsystemService(BaseSubsystemService baseSubsystemService) {
        this.baseSubsystemService = baseSubsystemService;
    }

    public void saveTemplates(String initFile, String objectName, String unitId,
            List<ImportDataTemplate> templates) {
        leadinImportDataTemplateDao.saveTemplates(initFile, objectName, unitId, templates);
    }

    public Map<String, ImportObjectNode> findfieldCustomMap(String initFile, String objectName,
            String unitId) {
        Map<String, ImportObjectNode> nodeMap = new HashMap<String, ImportObjectNode>();

        // 上级单位
        final Map<String, Integer> unitMap = baseSubsystemService.getParentUnitMap(unitId);
        String[] unitIds = unitMap.keySet().toArray(new String[0]);

        List<ImportDataTemplate> templates = leadinImportDataTemplateDao.findTemplates(initFile,
                objectName, unitIds);

        // 排序，等级越低的单位排在前位，不能覆盖上级单位的设置
        Collections.sort(templates, new Comparator<ImportDataTemplate>() {
            public int compare(ImportDataTemplate o1, ImportDataTemplate o2) {
                Integer order1 = unitMap.get(o1.getUnitId());
                Integer order2 = unitMap.get(o2.getUnitId());

                if (order1 == null)
                    order1 = 0;

                if (order2 == null)
                    order1 = 0;

                return order1 - order2;
            }
        });

        for (ImportDataTemplate t : templates) {
            ImportObjectNode node = new ImportObjectNode();
            node.setName(t.getFieldName());
            node.setChecked(true);
            if (!(t.getUnitId().equals(unitId))) {
                node.setDisabled(true);//非自身单位设置的不可以修改
            }
            nodeMap.put(t.getFieldName(), node);
        }

        return nodeMap;
    }

}
