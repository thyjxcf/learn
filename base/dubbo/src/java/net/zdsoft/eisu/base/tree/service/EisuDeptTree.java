/* 
 * @(#)EisuInstituteTree.java    Created on May 19, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeItemParam;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.Institute;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;

/**
 * 部门
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 19, 2011 10:41:06 AM $
 */
public class EisuDeptTree extends InstituteTree {
    private XLoadTreeItemConverter<Dept> deptTreeItemConverter;

    public void setDeptTreeItemConverter(XLoadTreeItemConverter<Dept> deptTreeItemConverter) {
        this.deptTreeItemConverter = deptTreeItemConverter;
    }

    @Override
    protected void fillSchoolChildNodeParam(TreeParam param, TreeItemParam item) {
        item.setXmlSrc("/common/xtree/instituteDeptTreeXml.action");
        item.setItemLinkParams("parentId=" + param.getUnitId() + "&parentType="
                + Institute.PARENT_SCHOOL);
    }

    @Override
    protected void addSchoolChildNodes(XLoadTreeItem parentNode, TreeParam param) {
        super.addSchoolChildNodes(parentNode, param);

        // 学校子节点：部门
        if (EisuTreeConstant.getTreeDepth(param.getLayer()) >= EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_DEPARTMENT)) {
            
            // 显示院系时，只显示学校的直属部门，否则显示学校和院系下的所有部门
            List<Dept> depts = null;
            if (param.isShowInstitute()) {
                depts = deptService.getDirectDepts(param.getUnitId());
            } else {
                depts = deptService.getDepts(param.getUnitId(), Dept.TOP_GROUP_GUID);
            }
            
            for (Dept e : depts) {
                deptTreeItemConverter.buildTreeItem(e, param, parentNode, makeXTreeItemName());
            }
        }

    }
    
    @Override
    protected void addEduChildNodes(XLoadTreeItem parentNode, TreeParam param) {
        addSchoolChildNodes(parentNode, param);
    }
}
