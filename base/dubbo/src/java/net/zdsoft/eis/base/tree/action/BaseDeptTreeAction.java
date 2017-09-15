/* 
 * @(#)BaseTeacherTreeAction.java    Created on May 19, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.action;

import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.service.TreeService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 19, 2011 10:04:35 AM $
 */
public class BaseDeptTreeAction extends BaseTreeModelAction {

    private static final long serialVersionUID = -2198498734518741641L;

    protected TreeService eisDeptTree;
    protected TreeService eisuDeptTree;

    public void setEisDeptTree(TreeService eisDeptTree) {
        this.eisDeptTree = eisDeptTree;
    }

    public void setEisuDeptTree(TreeService eisuDeptTree) {
        this.eisuDeptTree = eisuDeptTree;
    }

    protected TreeService getTreeService() {
        if (SystemVersion.PRODUCT_EIS.equals(systemDeployService.getProductId())) {
            return eisDeptTree;
        } else {
            return eisDeptTree;
        }
    }

    /**
     * 部门
     * 
     * @return
     * @throws Exception
     */
    public String deptTree() throws Exception {
        treeParam.setLayer(TreeConstant.ITEMTYPE_DEPARTMENT);
        return buildTree();
    }

}
