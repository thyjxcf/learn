/* 
 * @(#)TreeItemParam.java    Created on Jun 10, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.param;

/**
 * 树节点参数
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 10, 2011 3:46:59 PM $
 */
public class TreeItemParam {
    private String xmlSrc;// 不带参数
    private String itemLinkParams;// 节点参数

    public String getXmlSrc() {
        return xmlSrc;
    }

    public void setXmlSrc(String xmlSrc) {
        this.xmlSrc = xmlSrc;
    }

    public String getItemLinkParams() {
        return itemLinkParams;
    }

    public void setItemLinkParams(String itemLinkParams) {
        this.itemLinkParams = itemLinkParams;
    }

}
