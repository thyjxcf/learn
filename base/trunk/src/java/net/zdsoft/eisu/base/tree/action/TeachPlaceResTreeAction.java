/* 
 * @(#)TeachPlaceResTreeAction.java    Created on May 31, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.action;

import net.zdsoft.eis.base.tree.action.BaseTreeModelAction;
import net.zdsoft.eis.base.tree.service.TreeService;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2011 4:52:35 PM $
 */
public class TeachPlaceResTreeAction extends BaseTreeModelAction {
    private static final long serialVersionUID = 595954262915113213L;

    private TreeService teachAreaTree;

    public void setTeachAreaTree(TreeService teachAreaTree) {
        this.teachAreaTree = teachAreaTree;
    }

    @Override
    protected TreeService getTreeService() {
        return teachAreaTree;
    }

    /**
     * 教学区树
     * 
     * @return
     * @throws Exception
     */
    public String teachAreaTree() throws Exception {
        treeParam.setLayer(EisuTreeConstant.ITEMTYPE_TEACH_AREA);
        return buildTree();
    }

    /**
     * 教学场地树
     * 
     * @return
     * @throws Exception
     */
    public String teachPlaceTree() throws Exception {
        treeParam.setLayer(EisuTreeConstant.ITEMTYPE_TEACH_PLACE);
        return buildTree();
    }

    /**
     * 教学场地树
     * 
     * @return
     * @throws Exception
     */
    public String teachPlaceResTree() throws Exception {
        treeParam.setLayer(EisuTreeConstant.ITEMTYPE_TEACH_PLACE_RES);
        return buildTree();
    }
    
    /**
     * 班级树
     * 
     * @return
     * @throws Exception
     */
    public String teachPlaceClassTree() throws Exception {
        treeParam.setLayer(EisuTreeConstant.ITEMTYPE_CLASS);
        return buildTree();
    }
    
}
