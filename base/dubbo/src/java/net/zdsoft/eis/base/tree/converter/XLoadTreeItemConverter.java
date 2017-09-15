/* 
 * @(#)XLoadTreeItemConverter.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.converter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.leadin.tree.XLoadTreeItem;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 11:05:29 AM $
 */
public abstract class XLoadTreeItemConverter<E extends BaseEntity> {
    private static final Logger log = LoggerFactory.getLogger(XLoadTreeItemConverter.class);

    /**
     * 将entity转化为树节点
     * 
     * @param e
     * @param param
     * @param item
     */
    protected abstract void toTreeItem(E e, TreeParam param, XLoadTreeItem item);

    public List<XLoadTreeItem> buildTreeItems(List<E> entities, TreeParam param) {
        List<XLoadTreeItem> items = new ArrayList<XLoadTreeItem>();
        for (E e : entities) {
            items.add(buildTreeItem(e, param));
        }
        return items;
    }

    public XLoadTreeItem buildTreeItem(E e, TreeParam param) {
        return buildTreeItem(e, param, null, null);
    }

    public XLoadTreeItem buildTreeItem(XLoadTreeItem item, XLoadTreeItem parentNode, String nodeName) {
        if (StringUtils.isNotEmpty(nodeName))
            item.setNodeName(nodeName);

        if (null != parentNode)
            parentNode.appendNode(item);

        return item;
    }

    public XLoadTreeItem buildTreeItem(E e, TreeParam param, XLoadTreeItem parentNode,
            String nodeName) {
        XLoadTreeItem item = new XLoadTreeItem();
        item.setInputValue(e.getId());

        // 将entity转化为TreeItem
        toTreeItem(e, param, item);

        // 重构action，如果不是所有链接都开放，则只有最底层节点有链接
        if (!param.isAllLinkOpen() && param.getLayer() != item.getItemType()) {
            item.setAction(null);
        }

        // 重构图标
        item.setIcon(param.getContextPath() + TreeConstant.TREE_ICON_PATH_PREFIX + item.getIcon());
        if (StringUtils.isNotEmpty(item.getOpenIcon())) {
            item.setOpenIcon(param.getContextPath() + TreeConstant.TREE_ICON_PATH_PREFIX
                    + item.getOpenIcon());
        } else {
            item.setOpenIcon(item.getIcon());
        }

        // 如果到了公共树的展现层次外，还有下级业务节点，则展现下级节点
        if (param.getLayer() == item.getItemType()
                && StringUtils.isNotEmpty(param.getCustomXmlSrc())) {
            item.setXmlSrc(param.getCustomXmlSrc());
        }

        item.setXmlSrc(wrapXmlSrc(item.getXmlSrc(), item.getItemLinkParams(), param));
        if (StringUtils.isNotEmpty(nodeName))
            item.setNodeName(nodeName);

        if (null != parentNode)
            parentNode.appendNode(item);

        return item;
    }

    public String wrapXmlSrc(String xmlSrc,String itemLinkParams,TreeParam param){
        // 重构xmlSrc
        if (StringUtils.isNotEmpty(xmlSrc)) {
            StringBuilder sb = new StringBuilder();
            sb.append(param.getContextPath()).append(xmlSrc);
            String connector = "?";
            if (xmlSrc.contains("?")) {
                connector = "&";
            }
            sb.append(connector);
            if (StringUtils.isNotEmpty(itemLinkParams)) {
                sb.append(itemLinkParams);
            }
            sb.append("&allLinkOpen=").append(param.isAllLinkOpen());
            sb.append("&needAllPopedom=").append(param.isNeedAllPopedom());
            sb.append("&needDirectPopedom=").append(param.isNeedDirectPopedom());
            sb.append("&graduateClass=").append(param.isGraduateClass());
            sb.append("&showEdu=").append(param.isShowEdu());
            sb.append("&showSch=").append(param.isShowSch());
            sb.append("&showSchDistrict=").append(param.isShowSchDistrict());
            
            // 过滤树节点，减少树层次，方便用户使用
            sb.append("&showInstitute=").append(param.isShowInstitute());
            sb.append("&showSpecialtyPoint=").append(param.isShowSpecialtyPoint());
            sb.append("&showNoneSpecialtyPoint=").append(param.isShowNoneSpecialtyPoint());
            sb.append("&showGrade=").append(param.isShowGrade());
            sb.append("&showInstituteCustom=").append(param.isShowInstituteCustom());
            sb.append("&showSpecialtyPointCustom=").append(param.isShowSpecialtyPointCustom());
            sb.append("&showGradeCustom=").append(param.isShowGradeCustom());
            sb.append("&onlyShowPopedom=").append(param.isOnlyShowPopedom());
            sb.append("&eduadmShowPopedom=").append(param.isEduadmShowPopedom());
            sb.append("&showValid=").append(param.isShowValid());
            sb.append("&showPreGraduateCls=").append(param.isShowPreGraduateCls());
            if (param.getTreeType() != 0) {
                sb.append("&treeType=").append(param.getTreeType());
            }
            if (param.getLayer() != 0) {
                sb.append("&layer=").append(param.getLayer());
            }
            if (param.getCustomLayer() != 0) {
                sb.append("&customLayer=").append(param.getCustomLayer());
            }
            if (StringUtils.isNotEmpty(param.getCustomXmlSrc())) {
                sb.append("&customXmlSrc=").append(param.getCustomXmlSrc());
            }
            if (StringUtils.isNotEmpty(param.getCustomLinkParams())) {
                sb.append("&customLinkParams=").append(param.getCustomLinkParams());
                try {
                    String linkParam = URLDecoder.decode(param.getCustomLinkParams(), "UTF-8");
                    sb.append("&").append(linkParam);
                } catch (UnsupportedEncodingException e1) {
                    log.error(e1.toString(), e1);
                }

            }
            if (StringUtils.isNotEmpty(param.getGraduateAcadyear())) {
                sb.append("&graduateAcadyear=").append(param.getGraduateAcadyear());
            }
            if (StringUtils.isNotEmpty(param.getOpenAcadyear())
            		&& sb.indexOf("&openAcadyear=") == -1) {
                sb.append("&openAcadyear=").append(param.getOpenAcadyear());
            }
            if(StringUtils.isNotEmpty(param.getSemester())
            		&& sb.indexOf("&semester=") == -1){
            	sb.append("&semester=").append(param.getSemester());
            }
            if (StringUtils.isNotEmpty(param.getUserId())) {
                sb.append("&userId=").append(param.getUserId());
            }
            if (StringUtils.isNotEmpty(param.getUserDeptId())) {
                sb.append("&userDeptId=").append(param.getUserDeptId());
            }
            if (param.getInstituteKind() != 0) {
                sb.append("&instituteKind=").append(param.getInstituteKind());
            }
            if (StringUtils.isNotEmpty(param.getAcadyear())) {
                sb.append("&acadyear=").append(param.getAcadyear());
            }
            if (param.getUnitClass() != 0) {
                sb.append("&unitClass=").append(param.getUnitClass());
            }
            if (StringUtils.isNotBlank(param.getTeacherId())){
            	sb.append("&teacherId=").append(param.getTeacherId());
            }
            if (StringUtils.isNotBlank(param.getType())){
            	sb.append("&type=").append(param.getType());
            }
            if (StringUtils.isNotBlank(param.getRegionCode())){
            	sb.append("&regionCode=").append(param.getRegionCode());
            }
            if(param.isShowDeptShortName()){
            	sb.append("&showDeptShortName=").append(param.isShowDeptShortName());
            }
            
            //东莞人事-教师档案-个人信息-籍贯选择使用
            if(param.isDgPersonnel()){
            	sb.append("&dgPersonnel=").append(param.isDgPersonnel());
            }
            
            return sb.toString();
        } else {
            return "";
        }

    }
    public Element buildTreeItem(E e, TreeParam param, Element root) {
        XLoadTreeItem item = buildTreeItem(e, param, null, null);

        Element ele = root.addElement("tree");
        ele.addAttribute("text", item.getText());
        ele.addAttribute("action", item.getAction());
        ele.addAttribute("icon", item.getIcon());
        ele.addAttribute("openIcon", item.getOpenIcon());
        ele.addAttribute("src", item.getXmlSrc());
        return ele;
    }
}
