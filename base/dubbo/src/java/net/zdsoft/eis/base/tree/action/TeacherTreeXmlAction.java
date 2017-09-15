/* 
 * @(#)TeacherTreeXmlAction.java    Created on May 19, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.leadin.tree.XLoadTreeItem;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 19, 2011 2:54:14 PM $
 */
public class TeacherTreeXmlAction extends DeptTreeXmlAction {
    private static final long serialVersionUID = 7043861225908418079L;

    private TeacherService teacherService;
    private XLoadTreeItemConverter<Teacher> teacherTreeItemConverter;

    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public void setTeacherTreeItemConverter(XLoadTreeItemConverter<Teacher> teacherTreeItemConverter) {
        this.teacherTreeItemConverter = teacherTreeItemConverter;
    }

    /**
     * 点击部门节点，展示教师
     * 
     * @return
     * @throws Exception
     */
    public String expandDeptTeacher() throws Exception {
        List<XLoadTreeItem> items = new ArrayList<XLoadTreeItem>();

        // 部门节点
        items.addAll(getDeptItems());

        // 教师节点
        if (TreeConstant.getTreeDepth(treeParam.getLayer()) >= TreeConstant
                .getTreeDepth(TreeConstant.ITEMTYPE_TEACHER)) {
            List<Teacher> list = teacherService.getTeachersByDeptId(treeParam.getParentId());
            List<XLoadTreeItem> _items = teacherTreeItemConverter.buildTreeItems(list, treeParam);
            items.addAll(_items);
        }

        return buildTreeXml(items);
    }
}
