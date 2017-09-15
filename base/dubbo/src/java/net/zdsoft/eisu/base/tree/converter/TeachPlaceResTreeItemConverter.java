/* 
 * @(#)TeachPlaceResTreeItemConverter.java    Created on May 31, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.converter;

import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.TeachPlaceRes;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2011 4:25:06 PM $
 */
public class TeachPlaceResTreeItemConverter extends XLoadTreeItemConverter<TeachPlaceRes> {

    @Override
    public void toTreeItem(TeachPlaceRes e, TreeParam param, XLoadTreeItem item) {
       	//对SimpleObject置值
      	item.setId(e.getId());
    	item.setObjectName(e.getTeachResName());
    	
        int itemType = EisuTreeConstant.ITEMTYPE_TEACH_PLACE_RES;
        item.setText(e.getTeachResName());
        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getTeachResName()));
        item.setIcon("teachres.gif");

        item.setItemType(itemType);

    }

}
