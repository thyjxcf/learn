/* 
 * @(#)BasicClassTreeItemConverter.java    Created on Jun 2, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.converter;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 2, 2011 4:26:55 PM $
 */
public class BasicClassTreeItemConverter extends XLoadTreeItemConverter<BasicClass> {

    @Override
    protected void toTreeItem(BasicClass e, TreeParam param, XLoadTreeItem item) {
    	//对SimpleObject置值
    	item.setId(e.getId());
    	item.setObjectName(e.getClassnamedynamic());
    	
        int itemType = TreeConstant.ITEMTYPE_CLASS;
        item.setText(e.getClassnamedynamic());
        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getClassnamedynamic()));
        item.setIcon("class.gif");

        if (TreeConstant.getTreeDepth(param.getLayer()) >= TreeConstant
                .getTreeDepth(TreeConstant.ITEMTYPE_STUDENT)) {
            item.setXmlSrc("/common/xtree/eisStudentTreeXml.action");
        }

        item.setItemType(itemType);
        item.setItemLinkParams("classId=" + e.getId());

    }

}
