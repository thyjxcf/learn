/* 
 * @(#)TeachPlaceResTreeXmlAction.java    Created on May 31, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.action;

import java.util.List;

import net.zdsoft.eis.base.tree.action.BaseTreeXmlModelAction;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eisu.base.common.entity.TeachArea;
import net.zdsoft.eisu.base.common.entity.TeachPlace;
import net.zdsoft.eisu.base.common.entity.TeachPlaceRes;
import net.zdsoft.eisu.base.common.service.TeachAreaService;
import net.zdsoft.eisu.base.common.service.TeachPlaceResService;
import net.zdsoft.eisu.base.common.service.TeachPlaceService;
import net.zdsoft.leadin.tree.XLoadTreeItem;

/**
 * 教学区 -> 教学场地 -> 教学场地资源 树
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2011 4:44:50 PM $
 */
public class TeachPlaceResTreeXmlAction extends BaseTreeXmlModelAction {
    private static final long serialVersionUID = -7540362122743805940L;

    private TeachAreaService teachAreaService;
    private TeachPlaceService teachPlaceService;
    private TeachPlaceResService teachPlaceResService;

    private XLoadTreeItemConverter<TeachArea> teachAreaTreeItemConverter;
    private XLoadTreeItemConverter<TeachPlace> teachPlaceTreeItemConverter;
    private XLoadTreeItemConverter<TeachPlaceRes> teachPlaceResTreeItemConverter;

    /**
     * 点击学校节点，展示教学区
     * 
     * @return
     * @throws Exception
     */
    public String expandTeachArea() throws Exception {
        List<TeachArea> list = teachAreaService.getTeachAreas(treeParam.getParentId());
        List<XLoadTreeItem> items = teachAreaTreeItemConverter.buildTreeItems(list, treeParam);
        return buildTreeXml(items);
    }

    /**
     * 点击教学区节点，展示教学场区
     * 
     * @return
     * @throws Exception
     */
    public String expandTeachPlace() throws Exception {
        List<TeachPlace> list = teachPlaceService.getTeachPlacesByAreaId(treeParam.getParentId());
        List<XLoadTreeItem> items = teachPlaceTreeItemConverter.buildTreeItems(list, treeParam);
        return buildTreeXml(items);
    }

    /**
     * 点击教学场地节点，展示教学资源
     * 
     * @return
     * @throws Exception
     */
    public String expandTeachPlaceRes() throws Exception {
        List<TeachPlaceRes> list = teachPlaceResService.getTeachPlaceResesByPlaceId(treeParam
                .getParentId());
        List<XLoadTreeItem> items = teachPlaceResTreeItemConverter.buildTreeItems(list, treeParam);
        return buildTreeXml(items);
    }

    public void setTeachAreaService(TeachAreaService teachAreaService) {
        this.teachAreaService = teachAreaService;
    }

    public void setTeachPlaceService(TeachPlaceService teachPlaceService) {
        this.teachPlaceService = teachPlaceService;
    }

    public void setTeachPlaceResService(TeachPlaceResService teachPlaceResService) {
        this.teachPlaceResService = teachPlaceResService;
    }

    public void setTeachAreaTreeItemConverter(
            XLoadTreeItemConverter<TeachArea> teachAreaTreeItemConverter) {
        this.teachAreaTreeItemConverter = teachAreaTreeItemConverter;
    }

    public void setTeachPlaceTreeItemConverter(
            XLoadTreeItemConverter<TeachPlace> teachPlaceTreeItemConverter) {
        this.teachPlaceTreeItemConverter = teachPlaceTreeItemConverter;
    }

    public void setTeachPlaceResTreeItemConverter(
            XLoadTreeItemConverter<TeachPlaceRes> teachPlaceResTreeItemConverter) {
        this.teachPlaceResTreeItemConverter = teachPlaceResTreeItemConverter;
    }

}
