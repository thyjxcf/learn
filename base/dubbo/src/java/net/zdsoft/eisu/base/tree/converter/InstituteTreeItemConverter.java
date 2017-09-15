/* 
 * @(#)InstituteTreeItemConverter.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.converter;

import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.Institute;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 11:03:08 AM $
 */
public class InstituteTreeItemConverter extends XLoadTreeItemConverter<Institute> {

    @Override
    public void toTreeItem(Institute e, TreeParam param, XLoadTreeItem item) {
       	//对SimpleObject置值
      	item.setId(e.getId());
    	item.setObjectName(e.getInstituteName());
		
        item.setText(e.getInstituteName());

        int itemType = EisuTreeConstant.ITEMTYPE_INSTITUTE;
        String icon = "institute";
        if (Institute.INSTITUTE_KIND_INSTITUTE == e.getInstituteKind()) {
            icon = "institute";
        } else if (Institute.INSTITUTE_KIND_DEPARTMENT == e.getInstituteKind()) {
            icon = "institute_department";
        }
        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getInstituteName()));
        item.setIcon(icon + ".gif");

        String src = null;
        switch (EisuTreeConstant.getTreeType(param.getLayer())) {
        case EisuTreeConstant.TREE_TYPE_STUDENT:// 学生：专业
            src = "instituteSpecialtyTreeXml.action";
            break;
        case EisuTreeConstant.TREE_TYPE_TEACHER:// 教师：部门
        	 src = "instituteGroupTreeXml.action";
             break;
        case EisuTreeConstant.TREE_TYPE_TEACHER_USER:// 教师用户：部门
            src = "instituteDeptTreeXml.action";
            break;
            
        default:
            src = "instituteTreeXml.action";// 院系
            break;
        }
        item.setXmlSrc("/common/xtree/" + src);

        item.setItemType(itemType);
        item.setItemLinkParams("parentId=" + e.getId() + "&parentType="
                + Institute.PARENT_INSTITUTE +"&unitId="+e.getUnitId());
        item.setItemKind(e.getInstituteKind());
    }

}
