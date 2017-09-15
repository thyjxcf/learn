/* 
 * @(#)TeachAreaTree.java    Created on May 31, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.service;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeItemParam;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eis.base.tree.service.EduTree;
import net.zdsoft.eisu.base.common.entity.TeachArea;
import net.zdsoft.eisu.base.common.service.TeachAreaService;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;

/**
 * 教学区树
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2011 4:53:40 PM $
 */
public class TeachAreaTree extends EduTree {
    private TeachAreaService teachAreaService;
    private XLoadTreeItemConverter<TeachArea> teachAreaTreeItemConverter;

    public void setTeachAreaService(TeachAreaService teachAreaService) {
        this.teachAreaService = teachAreaService;
    }

    public void setTeachAreaTreeItemConverter(
            XLoadTreeItemConverter<TeachArea> teachAreaTreeItemConverter) {
        this.teachAreaTreeItemConverter = teachAreaTreeItemConverter;
    }

    @Override
    protected void fillSchoolChildNodeParam(TreeParam param, TreeItemParam item) {
        item.setXmlSrc("/common/xtree/teachAreaTreeXml.action");
        item.setItemLinkParams("parentId=" + param.getUnitId());
    }

    @Override
    protected void addSchoolChildNodes(XLoadTreeItem parentNode, TreeParam param) {
        // 学校子节点：教学区
        if (EisuTreeConstant.getTreeDepth(param.getLayer()) >= EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_INSTITUTE)) {
            List<TeachArea> areas = new ArrayList<TeachArea>(); 
			if(param.getLayer() == EisuTreeConstant.ITEMTYPE_CLASS){
				TeachArea ar = new TeachArea();
				ar = new TeachArea();
				ar.setAreaName("无校区");
				ar.setId(BaseConstant.ZERO_GUID);
				areas.add(ar);
            }
            areas.addAll(teachAreaService.getTeachAreas(param.getUnitId()));
            for (TeachArea e : areas) {
                teachAreaTreeItemConverter.buildTreeItem(e, param, parentNode, makeXTreeItemName());
            }
        }

    }
}
