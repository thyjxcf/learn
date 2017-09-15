/* 
 * @(#)TeachPlaceTreeItemConverter.java    Created on May 31, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.converter;

import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.TeachPlace;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2011 4:24:32 PM $
 */
public class TeachPlaceTreeItemConverter extends XLoadTreeItemConverter<TeachPlace> {

    @Override
    public void toTreeItem(TeachPlace e, TreeParam param, XLoadTreeItem item) {
       	//对SimpleObject置值
      	item.setId(e.getId());
    	item.setObjectName(e.getPlaceName());
    	
        int itemType = EisuTreeConstant.ITEMTYPE_TEACH_PLACE;
        item.setText(e.getPlaceName());
        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getPlaceName()));
        item.setIcon("teachplace.gif");

        if (EisuTreeConstant.getTreeDepth(param.getLayer()) >= EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_TEACH_PLACE_RES)) {
            item.setXmlSrc("/common/xtree/teachPlaceResTreeXml.action");
        }

        item.setItemType(itemType);
        item.setItemLinkParams("parentId=" + e.getId());
    }

}
