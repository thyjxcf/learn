/* 
 * @(#)SchoolDistrictTreeItemConverter.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.converter;

import net.zdsoft.eis.base.common.entity.SchoolDistrict;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * 学区
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 3:10:14 PM $
 */
public class SchoolDistrictTreeItemConverter extends XLoadTreeItemConverter<SchoolDistrict> {

    @Override
    public void toTreeItem(SchoolDistrict e, TreeParam param, XLoadTreeItem item) {
    	//对SimpleObject置值
    	item.setId(e.getId());
    	item.setObjectName(e.getName());
    	
        item.setText(e.getName());

        int itemType = TreeConstant.ITEMTYPE_XQ;
        String icon = "schdistrict";       

        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getName()));
        item.setIcon(icon + ".gif");

        item.setXmlSrc("/common/xtree/schoolTreeXml.action");
        
        item.setItemType(itemType);
        item.setItemLinkParams("parentId=" + e.getId());
    }

}
