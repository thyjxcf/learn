/* 
 * @(#)EisuClassTree.java    Created on Jul 27, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.service;

import java.util.List;

import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeItemParam;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.EisuClass;
import net.zdsoft.eisu.base.common.service.EisuClassService;
import net.zdsoft.leadin.tree.XLoadTreeItem;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jul 27, 2011 4:59:20 PM $
 */
public class EisuClassTree extends EisuGradeTree {
    
    private EisuClassService eisuClassService;
    
    private XLoadTreeItemConverter<EisuClass> eisuClassTreeItemConverter;
    
    
    @Override
    public void fillSchoolChildNodeParam(TreeParam param, TreeItemParam item) {
        item.setXmlSrc("/common/xtree/eisClassTreeXml.action");
        item.setItemLinkParams("unitId=" + param.getUnitId());
    }

    @Override
    protected void addSchoolChildNodes(XLoadTreeItem parentNode, TreeParam param) {
        if (TreeConstant.getTreeDepth(param.getLayer()) >= TreeConstant
                .getTreeDepth(TreeConstant.ITEMTYPE_CLASS)) {
            List<EisuClass> classes = null;
            String schoolId = param.getUnitId();
            if(StringUtils.isNotEmpty(param.getGradeId())){
            	classes = eisuClassService.getClassByGradeId(schoolId, param.getGradeId());
            }else if(StringUtils.isNotEmpty(param.getOpenAcadyear())){
                classes = eisuClassService.getClassesByOpenAcadyear(schoolId, param.getOpenAcadyear());
            } else {
                classes = eisuClassService.getClasses(schoolId);
            }
            for (EisuClass e : classes) {
                eisuClassTreeItemConverter.buildTreeItem(e, param, parentNode, makeXTreeItemName());
            }
        }
    }
    
    public void wrapTreeParam(TreeParam param) {
        super.wrapTreeParam(param);       
    }



    /**
     * 设置eisuClassService
     * @param eisuClassService eisuClassService
     */
    public void setEisuClassService(EisuClassService eisuClassService) {
        this.eisuClassService = eisuClassService;
    }

    /**
     * 设置eisuClassTreeItemConverter
     * @param eisuClassTreeItemConverter eisuClassTreeItemConverter
     */
    public void setEisuClassTreeItemConverter(XLoadTreeItemConverter<EisuClass> eisuClassTreeItemConverter) {
        this.eisuClassTreeItemConverter = eisuClassTreeItemConverter;
    }
}
