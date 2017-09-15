/* 
 * @(#)TeacherTreeItemConverter.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.converter;

import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 3:23:15 PM $
 */
public class TeacherTreeItemConverter extends XLoadTreeItemConverter<Teacher> {

    @Override
    public void toTreeItem(Teacher e, TreeParam param, XLoadTreeItem item) {
    	//对SimpleObject置值
    	item.setId(e.getId());
    	item.setObjectCode(e.getTchId());
    	item.setObjectName(e.getName());
//    	item.setUnitiveCode("");
//		item.setGroupId(e.getDeptid());
//		item.setUnitId(e.getUnitid());
		
		
        int itemType = TreeConstant.ITEMTYPE_TEACHER;
        item.setText(e.getName());
        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getName(), e.getDeptid()));

        // 根据性别显示男女图标
        if (BaseConstant.SEX_MALE.equals(e.getSex())) {
            item.setIcon("male.gif");
        } else if (BaseConstant.SEX_FEMALE.equals(e.getSex())) {
            item.setIcon("female.gif");
        } else {
            item.setIcon("unknown_sex.gif");
        }

        item.setItemType(itemType);

    }

}
