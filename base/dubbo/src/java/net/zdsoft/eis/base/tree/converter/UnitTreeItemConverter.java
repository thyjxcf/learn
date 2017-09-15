/* 
 * @(#)UnitTreeItemConverter.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.converter;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 3:10:14 PM $
 */
public class UnitTreeItemConverter extends XLoadTreeItemConverter<Unit> {

    @Override
    public void toTreeItem(Unit e, TreeParam param, XLoadTreeItem item) {
    	//对SimpleObject置值
    	item.setId(e.getId());
    	item.setObjectName(e.getName());
				
        item.setText(e.getName());

        int itemType = TreeConstant.ITEMTYPE_EDUCATION;
        String icon = "edu";
        if (Unit.UNIT_CLASS_EDU == e.getUnitclass().intValue()) {
            itemType = TreeConstant.ITEMTYPE_EDUCATION;
            icon = "edu";
        } else if (Unit.UNIT_CLASS_SCHOOL == e.getUnitclass().intValue()) {
            itemType = TreeConstant.ITEMTYPE_SCHOOL;
            icon = "school";
        }

        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getName()));
        item.setIcon(icon + ".gif");
        
        item.setItemType(itemType);
//        item.setXmlSrc("/common/xtree/unitTreeXml.action");
//        item.setItemLinkParams("parentId=" + e.getId());
    }

}
