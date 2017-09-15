/* 
 * @(#)SpecialtyPointTreeItemConverter.java    Created on Jul 26, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.converter;

import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.SpecialtyPoint;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jul 26, 2011 2:30:42 PM $
 */
public class SpecialtyPointTreeItemConverter extends XLoadTreeItemConverter<SpecialtyPoint> {

    @Override
    protected void toTreeItem(SpecialtyPoint e, TreeParam param, XLoadTreeItem item) {
       	//对SimpleObject置值
      	item.setId(e.getId());
    	item.setObjectName(e.getPointName());
    	
        int itemType = EisuTreeConstant.ITEMTYPE_SPECIALTY_POINT;
        item.setText(e.getPointName());
        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getPointName(), e
                .getSpecialtyId()));
        item.setIcon("specialtypoint.gif");

        if (EisuTreeConstant.getTreeDepth(param.getLayer()) >= EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_GRADE)) {
            item.setXmlSrc("/common/xtree/eisuGradeTreeXml.action");
        }

        item.setItemType(itemType);
        item.setItemLinkParams("specialtyId=" + e.getSpecialtyId() + "&specialtyPointId="
                + e.getId() + "&unitId=" + e.getUnitId());

    }

}
