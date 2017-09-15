/* 
 * @(#)SpecialtyPointTreeAction.java    Created on Jul 26, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.action;

import net.zdsoft.eis.base.tree.action.BaseTreeModelAction;
import net.zdsoft.eis.base.tree.service.TreeService;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;

/**
 * 专业方向树
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jul 26, 2011 4:37:28 PM $
 */
public class SpecialtyPointTreeAction extends BaseTreeModelAction {
    private static final long serialVersionUID = 2838909038741393818L;

    protected TreeService specialtyPointTree;

    public void setSpecialtyPointTree(TreeService specialtyPointTree) {
        this.specialtyPointTree = specialtyPointTree;
    }

    @Override
    protected TreeService getTreeService() {
        return specialtyPointTree;
    }

    /**
     * 院系 -> 专业 -> 专业方向
     * 
     * @return
     * @throws Exception
     */
    public String specialtyPointTree() throws Exception {
        treeParam.setLayer(EisuTreeConstant.ITEMTYPE_SPECIALTY_POINT);
        return buildTree();
    }

}
