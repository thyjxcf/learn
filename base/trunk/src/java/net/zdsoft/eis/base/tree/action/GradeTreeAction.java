/* 
 * @(#)BaseGradeTreeAction.java    Created on May 25, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.action;

import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.service.TreeService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 25, 2011 11:17:20 AM $
 */
public class GradeTreeAction extends BaseTreeModelAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 869870436889118456L;

    protected TreeService eisGradeTree;
    protected TreeService eisuGradeTree;

    public void setEisGradeTree(TreeService eisGradeTree) {
        this.eisGradeTree = eisGradeTree;
    }

    public void setEisuGradeTree(TreeService eisuGradeTree) {
        this.eisuGradeTree = eisuGradeTree;
    }

    protected TreeService getTreeService() {
        if (SystemVersion.PRODUCT_EIS.equals(systemDeployService.getProductId())) {
            return eisGradeTree;
        } else {
            return eisuGradeTree;
        }
    }

    /**
     * 年级
     * 
     * @return
     * @throws Exception
     */
    public String gradeTree() throws Exception {
        treeParam.setLayer(TreeConstant.ITEMTYPE_GRADE);
        return buildTree();
    }
}
