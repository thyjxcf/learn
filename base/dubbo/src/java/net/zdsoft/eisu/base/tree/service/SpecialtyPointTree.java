/* 
 * @(#)SpecialtyPointTree.java    Created on Jul 27, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.service;

import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;

/**
 * 专业方向树
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jul 27, 2011 4:51:23 PM $
 */
public class SpecialtyPointTree extends SpecialtyTree {
    public void wrapTreeParam(TreeParam param) {
        super.wrapTreeParam(param);

        if (param.isShowSpecialtyPointCustom())
            return;
        
        if (EisuTreeConstant.getTreeDepth(param.getLayer()) > EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_SPECIALTY_POINT)
                || (param.getLayer() == EisuTreeConstant.ITEMTYPE_SPECIALTY_POINT && param
                        .getCustomLayer() != 0)) {
            param.setShowSpecialtyPoint(unitIniService.getUnitOptionBooleanValue(param.getUnitId(),
                    "SYSTEM.TREE.SHOW.SPECIALTY.POINT.SWITCH"));
        }
    }
}
