/* 
 * @(#)DeptTreeItemConverter.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.converter;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 3:10:52 PM $
 */
public class DeptTreeItemConverter extends XLoadTreeItemConverter<Dept> {
    
    @Override
    public void toTreeItem(Dept e, TreeParam param, XLoadTreeItem item) {
    	//对SimpleObject置值
    	item.setId(e.getId());
    	if(param.isShowDeptShortName()){
    		if(StringUtils.isNotEmpty(e.getDeptShortName())){
    			item.setObjectName(e.getDeptname()+"("+e.getDeptShortName()+")");	
    		}else{
    			item.setObjectName(e.getDeptname());
    		}
    	}else{
    		item.setObjectName(e.getDeptname());	
    	}
		
        item.setText(e.getDeptname());

        int itemType = TreeConstant.ITEMTYPE_DEPARTMENT;
        String icon = "department";
        if (Dept.SECTION_OFFICE_MARK == e.getJymark().intValue()) {
            icon = "department";
        } else if (Dept.STUFF_ROOM_MARK == e.getJymark().intValue()) {
            icon = "department";
        }
        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getDeptname()));
        item.setIcon(icon + ".gif");

        String src = null;
        switch (TreeConstant.getTreeType(param.getLayer())) {
        case TreeConstant.TREE_TYPE_TEACHER:// 教师
            src = "deptTeacherTreeXml.action";
            break;
        case TreeConstant.TREE_TYPE_TEACHER_USER:// 教师用户
            src = "deptUserTreeXml.action";
            break;
        default:
            src = "deptTreeXml.action";// 部门
            break;
        }
       
        item.setXmlSrc("/common/xtree/" + src);
        item.setItemType(itemType);
        item.setItemLinkParams("parentId=" + e.getId() + "&parentType=" + Dept.PARENT_DEPT);

    }
}
