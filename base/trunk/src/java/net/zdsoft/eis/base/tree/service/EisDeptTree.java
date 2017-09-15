/* 
 * @(#)EisDeptTree.java    Created on Jun 2, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeItemParam;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.leadin.tree.XLoadTreeItem;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 2, 2011 3:38:19 PM $
 */
public class EisDeptTree extends EduTree {
    private DeptService deptService;
    private XLoadTreeItemConverter<Dept> deptTreeItemConverter;

    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }

    public void setDeptTreeItemConverter(XLoadTreeItemConverter<Dept> deptTreeItemConverter) {
        this.deptTreeItemConverter = deptTreeItemConverter;
    }

    @Override
    protected void fillSchoolChildNodeParam(TreeParam param, TreeItemParam item) {
        item.setXmlSrc("/common/xtree/deptTreeXml.action");
        item.setItemLinkParams("parentId=" + param.getUnitId() + "&parentType="
                + Dept.PARENT_SCHOOL);
    }

    @Override
    protected void addSchoolChildNodes(XLoadTreeItem parentNode, TreeParam param) {

        // 学校子节点：部门
        if (TreeConstant.getTreeDepth(param.getLayer()) >= TreeConstant
                .getTreeDepth(TreeConstant.ITEMTYPE_DEPARTMENT)) {
            List<Dept> depts = deptService.getDirectDepts(param.getUnitId());
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
