/* 
 * @(#)GradeTreeItemConverter.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.converter;

import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.EisuGrade;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 2:52:32 PM $
 */
public class EisuGradeTreeItemConverter extends XLoadTreeItemConverter<EisuGrade> {

    public void toTreeItem(EisuGrade e, TreeParam param, XLoadTreeItem item) {
    	//对SimpleObject置值
    	item.setId(e.getId());
    	item.setObjectName(e.getGradename());
    	
        int itemType = EisuTreeConstant.ITEMTYPE_GRADE;

        item.setInputValue(e.getAcadyear());
        item.setText(e.getGradename());
        item.setAction(XTreeMaker.creatAction(e.getAcadyear(), itemType, e.getGradename(), param
                .getSpecialtyId(),param.getSpecialtyPointId()));
        item.setIcon("grade.gif");

        if (EisuTreeConstant.getTreeDepth(param.getLayer()) >= EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_CLASS)) {
            item.setXmlSrc("/common/xtree/eisuClassTreeXml.action");
        }

        item.setItemType(itemType);
        item.setItemLinkParams("specialtyId=" + param.getSpecialtyId() + "&specialtyPointId="
                + (param.getSpecialtyPointId() == null ? "" : param.getSpecialtyPointId())
                + "&openAcadyear=" + e.getAcadyear());
    }

}
