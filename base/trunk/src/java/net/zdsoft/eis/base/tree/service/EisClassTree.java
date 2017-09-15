/* 
 * @(#)EisClassTree.java    Created on Jun 2, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeItemParam;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.leadin.tree.XLoadTreeItem;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 2, 2011 4:21:55 PM $
 */
public class EisClassTree extends EisGradeTree {
    private BasicClassService basicClassService;
    private XLoadTreeItemConverter<BasicClass> basicClassTreeItemConverter;

    public void setBasicClassService(BasicClassService basicClassService) {
        this.basicClassService = basicClassService;
    }

    public void setBasicClassTreeItemConverter(
            XLoadTreeItemConverter<BasicClass> basicClassTreeItemConverter) {
        this.basicClassTreeItemConverter = basicClassTreeItemConverter;
    }

    @Override
    public void fillSchoolChildNodeParam(TreeParam param, TreeItemParam item) {
        item.setXmlSrc("/common/xtree/eisClassTreeXml.action");
        item.setItemLinkParams("unitId=" + param.getUnitId());
    }

    @Override
    protected void addSchoolChildNodes(XLoadTreeItem parentNode, TreeParam param) {
    	if(param.isShowEisGrade()) {
    		super.addSchoolChildNodes(parentNode, param);
    	}else {
    		// 学校子节点：班级
    		if (TreeConstant.getTreeDepth(param.getLayer()) >= TreeConstant
    				.getTreeDepth(TreeConstant.ITEMTYPE_CLASS)) {
    			List<BasicClass> classes = null;
    			String schoolId = param.getUnitId();
    			if (param.isGraduateClass()) {
    				classes = basicClassService.getClasses(schoolId,param.getAcadyear());
    			}else if(StringUtils.isNotEmpty(param.getGradeId())){
    				classes = basicClassService.getClassesBySchoolIdGradeId(schoolId, param.getGradeId());
    				
                }else if(StringUtils.isNotEmpty(param.getOpenAcadyear())){
                    classes = basicClassService.getClassesByOpenAcadyear(schoolId, param.getOpenAcadyear());
                    
                } else {
    				classes = basicClassService.getClasses(schoolId);
    			}
    			for (BasicClass e : classes) {
    				basicClassTreeItemConverter
    				.buildTreeItem(e, param, parentNode, makeXTreeItemName());
    			}
    		}
    	}
    }
}
