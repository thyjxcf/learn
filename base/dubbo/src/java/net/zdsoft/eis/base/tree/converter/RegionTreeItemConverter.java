/* 
 * @(#)RegionTreeItemConverter.java    Created on 2014-3-24
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.converter;

import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2014-3-24 上午10:25:26 $
 */
public class RegionTreeItemConverter extends XLoadTreeItemConverter<Region> {

	@Override
	protected void toTreeItem(Region e, TreeParam param, XLoadTreeItem item) {
		//对SimpleObject置值
    	item.setId(e.getFullCode());
    	item.setObjectName(e.getRegionName());
    	item.setFullName(e.getFullName());
		
        item.setText(e.getRegionName());

        int itemType = 0;
        String icon = "folder.png";
        item.setAction(XTreeMaker.creatAction(e.getFullCode(), itemType, e.getFullName()));
        item.setIcon(icon);

        String src = "regionTreeXml.action";      
       
        item.setXmlSrc("/common/xtree/" + src);
        item.setItemType(itemType);
        item.setItemLinkParams("parentId=" + e.getRegionCode());

	}

}
