/* 
 * @(#)BaseClassTreeAction.java    Created on May 25, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.action;

import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.service.TreeService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 25, 2011 11:30:15 AM $
 */
public class BaseClassTreeAction extends BaseTreeModelAction {
    private static final long serialVersionUID = -8716647606147519752L;

    protected TreeService eisClassTree;
    protected TreeService eisuClassTree;

    public void setEisClassTree(TreeService eisClassTree) {
        this.eisClassTree = eisClassTree;
    } 

    public void setEisuClassTree(TreeService eisuClassTree) {
        this.eisuClassTree = eisuClassTree;
    }

	protected TreeService getTreeService() {
        if (SystemVersion.PRODUCT_EIS.equals(systemDeployService.getProductId())) {
            return eisClassTree;
        } else {
            return eisuClassTree;
        }
    }

    /**
     * 班级
     * 
     * @return
     * @throws Exception
     */
    public String classTree() throws Exception {
        treeParam.setShowEdu(true);
        treeParam.setShowSch(true);
        treeParam.setLayer(TreeConstant.ITEMTYPE_CLASS);
        return buildTree();
    }
}
