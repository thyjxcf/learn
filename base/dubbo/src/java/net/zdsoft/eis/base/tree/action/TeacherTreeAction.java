/* 
 * @(#)TeacherTreeAction.java    Created on May 19, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.action;

import net.zdsoft.eis.base.tree.TreeConstant;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 19, 2011 2:35:52 PM $
 */
public class TeacherTreeAction extends BaseDeptTreeAction {
    private static final long serialVersionUID = -5360071101508106051L;

    /**
     * 部门 -> 教师
     * 
     * @return
     * @throws Exception
     */
    public String teacherTree() throws Exception {
        treeParam.setLayer(TreeConstant.ITEMTYPE_TEACHER);
        return buildTree();
    }
}
