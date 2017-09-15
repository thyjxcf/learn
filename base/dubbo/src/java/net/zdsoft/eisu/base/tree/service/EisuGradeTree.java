/* 
 * @(#)EisuGradeTree.java    Created on Jul 27, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.service;

import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jul 27, 2011 4:52:57 PM $
 */
public class EisuGradeTree extends SpecialtyPointTree {

    public void wrapTreeParam(TreeParam param) {
        super.wrapTreeParam(param);

        if (param.isShowGradeCustom())
            return;

        if (EisuTreeConstant.getTreeDepth(param.getLayer()) > EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_GRADE)
                || (param.getLayer() == EisuTreeConstant.ITEMTYPE_GRADE && param.getCustomLayer() != 0)) {
            param.setShowGrade(unitIniService.getUnitOptionBooleanValue(param.getUnitId(),
                    "SYSTEM.TREE.SHOW.GRADE.SWITCH"));
        }

    }
}
