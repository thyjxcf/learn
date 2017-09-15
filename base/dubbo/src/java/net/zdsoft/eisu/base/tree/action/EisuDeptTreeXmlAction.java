/* 
 * @(#)EisuDeptTreeXmlAction.java    Created on May 19, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 19, 2011 2:18:56 PM $
 */
public class EisuDeptTreeXmlAction extends InstituteTreeXmlAction {
    private static final long serialVersionUID = 1153108137918567464L;

    private DeptService deptService;
    private XLoadTreeItemConverter<Dept> deptTreeItemConverter;

    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }

    public void setDeptTreeItemConverter(XLoadTreeItemConverter<Dept> deptTreeItemConverter) {
        this.deptTreeItemConverter = deptTreeItemConverter;
    }

    /**
     * 点击学校或院系节点，展示系和部门
     * 
     * @return
     * @throws Exception
     */
    public String expandInstituteDept() throws Exception {
        List<XLoadTreeItem> items = new ArrayList<XLoadTreeItem>();

        // 系节点
        items.addAll(getInstituteItems());

        // 部门节点
        if (EisuTreeConstant.getTreeDepth(treeParam.getLayer()) >= EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_DEPARTMENT)) {
            List<Dept> list = null;
            
            // 显示院系时，只显示学校的直属部门，否则显示学校和院系下的所有部门
            if (treeParam.isShowInstitute()) {
                if (Dept.PARENT_SCHOOL == treeParam.getParentType()) {
                    list = deptService.getDirectDepts(treeParam.getParentId());
                } else if (Dept.PARENT_INSTITUTE == treeParam.getParentType()) {
                    list = deptService.getDirectDeptsByInstituteId(treeParam.getParentId());
                } else {
                    list = new ArrayList<Dept>();
                }
            } else {
                list = deptService.getDepts(treeParam.getUnitId(), Dept.TOP_GROUP_GUID);
            }
            
            List<XLoadTreeItem> _items = deptTreeItemConverter.buildTreeItems(list, treeParam);
            items.addAll(_items);
        }

        return buildTreeXml(items);
    }
}
