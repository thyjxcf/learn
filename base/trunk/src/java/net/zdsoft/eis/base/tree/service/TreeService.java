/* 
 * @(#)TreeService.java    Created on Jun 1, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.leadin.tree.XLoadTreeItem;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 1, 2011 5:51:34 PM $
 */
public interface TreeService {
    /**
     * 获取树
     * 
     * @param param
     * @return
     */
    public XLoadTreeItem getTree(TreeParam param);

    /**
     * 取权限Map
     * 
     * @param treeParam
     * @return
     */
    public Map<String, XLoadTreeItem> getPopedomTreeItemMap(TreeParam treeParam);

    /**
     * 是否返回
     * 
     * @param param
     * @return
     */
    public boolean isReturn(TreeParam param);
    
    /**
     * 设置一些参数
     * 
     * @param param
     */
    public void wrapTreeParam(TreeParam param);
    
    /**
     * 取有权限的对象
     * @param <E>
     * @param param
     * @return
     */
    public <E extends BaseEntity> List<E> getPopedomEntities(TreeParam param);
}
