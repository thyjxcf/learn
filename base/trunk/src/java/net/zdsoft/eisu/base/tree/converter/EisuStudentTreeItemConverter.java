/* 
 * @(#)EisuStudentTreeItemConverter.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.converter;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.EisuStudent;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 3:07:52 PM $
 */
public class EisuStudentTreeItemConverter extends XLoadTreeItemConverter<EisuStudent> {

    @Override
    public void toTreeItem(EisuStudent e, TreeParam param, XLoadTreeItem item) {
    	//对SimpleObject置值
    	item.setId(e.getId());
    	item.setObjectCode(e.getStucode());
    	item.setObjectName(e.getStuname());
//    	item.setUnitiveCode(e.getUnitivecode());
//		item.setGroupId(e.getClassid());
//		item.setUnitId(e.getSchid());
    	
        int itemType = EisuTreeConstant.ITEMTYPE_STUDENT;
        item.setText(e.getStuname());
        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getStuname(), e.getClassid()));

        // 根据性别显示男女图标
        if (Integer.parseInt(BaseConstant.SEX_MALE) == e.getSex().intValue()) {
            item.setIcon("male.gif");
        } else if (Integer.parseInt(BaseConstant.SEX_FEMALE) == e.getSex().intValue()) {
            item.setIcon("female.gif");
        } else {
            item.setIcon("unknown_sex.gif");
        }

        item.setItemType(itemType);
    }

}
