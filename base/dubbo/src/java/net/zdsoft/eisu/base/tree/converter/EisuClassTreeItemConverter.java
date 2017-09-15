/* 
 * @(#)EisuClassTreeItemConverter.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.converter;

import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.EisuClass;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 2:56:34 PM $
 */
public class EisuClassTreeItemConverter extends XLoadTreeItemConverter<EisuClass> {

    @Override
    public void toTreeItem(EisuClass e, TreeParam param, XLoadTreeItem item) {
    	//对SimpleObject置值
    	item.setId(e.getId());
    	item.setObjectName(e.getClassnamedynamic());
    	
        int itemType = EisuTreeConstant.ITEMTYPE_CLASS;
        item.setText(e.getClassname());
        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getClassname()));
        item.setIcon("class.gif");

        if (EisuTreeConstant.getTreeDepth(param.getLayer()) >= EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_STUDENT)) {
            item.setXmlSrc("/common/xtree/eisuStudentTreeXml.action");
        }

        item.setItemType(itemType);
        item.setItemLinkParams("classId=" + e.getId());
    }

}
