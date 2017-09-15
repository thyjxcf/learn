/* 
 * @(#)InstituteTreeAction.java    Created on May 19, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.action;

import net.zdsoft.eis.base.tree.action.BaseTreeModelAction;
import net.zdsoft.eis.base.tree.service.TreeService;
import net.zdsoft.eisu.base.common.entity.Institute;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 19, 2011 1:59:36 PM $
 */
public class InstituteTreeAction extends BaseTreeModelAction {
    private static final long serialVersionUID = 8923172407079205038L;

    private TreeService instituteTree;

    public void setInstituteTree(TreeService instituteTree) {
        this.instituteTree = instituteTree;
    }

    @Override
    protected TreeService getTreeService() {
        return instituteTree;
    }

    /**
     * 院树
     * 
     * @return
     * @throws Exception
     */
    public String instituteTree() throws Exception {
        treeParam.setInstituteKind(Institute.INSTITUTE_KIND_INSTITUTE);
        treeParam.setLayer(EisuTreeConstant.ITEMTYPE_INSTITUTE);
        return buildTree();
    }

    /**
     * 院系树
     * 
     * @return
     * @throws Exception
     */
    public String instituteDeptTree() throws Exception {
        treeParam.setLayer(EisuTreeConstant.ITEMTYPE_INSTITUTE);
        return buildTree();
    }
}
