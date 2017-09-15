/* 
 * @(#)InstituteTreeAction.java    Created on May 16, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.action;

import net.zdsoft.eis.base.tree.action.BaseTreeModelAction;
import net.zdsoft.eis.base.tree.service.TreeService;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;

/**
 * 专业树
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 16, 2011 4:40:55 PM $
 */
public class SpecialtyTreeAction extends BaseTreeModelAction {

    private static final long serialVersionUID = -8501199578512129449L;

    protected TreeService specialtyTree;

    public void setSpecialtyTree(TreeService specialtyTree) {
        this.specialtyTree = specialtyTree;
    }

    protected TreeService getTreeService() {
        return specialtyTree;
    }

    /**
     * 院系 -> 专业
     * 
     * @return
     * @throws Exception
     */
    public String specialtyTree() throws Exception {
        treeParam.setLayer(EisuTreeConstant.ITEMTYPE_SPECIALTY);
        return buildTree();
    }

}
