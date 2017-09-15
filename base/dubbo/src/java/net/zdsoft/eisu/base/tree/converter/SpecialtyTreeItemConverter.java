/* 
 * @(#)SpecialtyTreeItemConverter.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.converter;

import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.Specialty;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 2:36:51 PM $
 */
public class SpecialtyTreeItemConverter extends XLoadTreeItemConverter<Specialty> {

    public void toTreeItem(Specialty e, TreeParam param, XLoadTreeItem item) {
       	//对SimpleObject置值
      	item.setId(e.getId());
    	item.setObjectName(e.getSpecialtyName());
    	
        int itemType = EisuTreeConstant.ITEMTYPE_SPECIALTY;
        item.setText(e.getSpecialtyName());
        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getSpecialtyName()));
        item.setIcon("specialty.gif");

        if (EisuTreeConstant.getTreeDepth(param.getLayer()) >= EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_SPECIALTY_POINT)) {
            item.setXmlSrc("/common/xtree/specialtyPointTreeXml.action");//专业方向或年级
        }

        item.setItemType(itemType);
        item.setItemLinkParams("specialtyId=" + e.getId() + "&unitId=" + e.getUnitId());
    }
}
