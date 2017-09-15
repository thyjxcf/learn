/* 
 * @(#)StudentTreeItemConverter.java    Created on Jun 2, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.converter;

import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 2, 2011 4:33:45 PM $
 */
public class StudentTreeItemConverter extends XLoadTreeItemConverter<Student> {

    @Override
    protected void toTreeItem(Student e, TreeParam param, XLoadTreeItem item) {
    	//对SimpleObject置值
    	item.setId(e.getId());
    	item.setObjectCode(e.getStucode());
    	item.setObjectName(e.getStuname());
//    	item.setUnitiveCode(e.getUnitivecode());
//		item.setGroupId(e.getClassid());
//		item.setUnitId(e.getSchid());
    	
        int itemType = TreeConstant.ITEMTYPE_STUDENT;
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
