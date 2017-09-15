/* 
 * @(#)TeachAreaTreeItemConverter.java    Created on May 31, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.converter;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.TeachArea;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2011 4:23:43 PM $
 */
public class TeachAreaTreeItemConverter extends XLoadTreeItemConverter<TeachArea> {

    @Override
    public void toTreeItem(TeachArea e, TreeParam param, XLoadTreeItem item) {
       	//对SimpleObject置值
      	item.setId(e.getId());
    	item.setObjectName(e.getAreaName());
    	
        int itemType = EisuTreeConstant.ITEMTYPE_TEACH_AREA;
        item.setText(e.getAreaName());
        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getAreaName()));
        item.setIcon("teacharea.gif");

        if (EisuTreeConstant.getTreeDepth(param.getLayer()) >= EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_TEACH_PLACE)) {
            item.setXmlSrc("/common/xtree/teachPlaceTreeXml.action");
        } else if(EisuTreeConstant.getTreeDepth(param.getLayer()) == EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_CLASS)){
        	item.setXmlSrc("/common/xtree/eisuClassTreeXml.action");
        	if(StringUtils.isNotEmpty(param.getSemester())){
        		item.setXmlSrc("/eduadm/common/xtree/setClassTreeXml.action");
        	}
        }

        item.setItemType(itemType);
        item.setItemLinkParams("parentId=" + e.getId());

    }

}
