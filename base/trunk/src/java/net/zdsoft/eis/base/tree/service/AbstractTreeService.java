/* 
 * @(#)AbstractTreeService.java    Created on 2014-3-24
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2014-3-24 上午11:07:23 $
 */
public abstract class AbstractTreeService extends XTreeMaker implements TreeService {

	@Override
	public Map<String, XLoadTreeItem> getPopedomTreeItemMap(TreeParam treeParam) {
		return null;
	}

	@Override
	public boolean isReturn(TreeParam param) {
		return false;
	}

	@Override
	public void wrapTreeParam(TreeParam param) {

	}

	@Override
	public <E extends BaseEntity> List<E> getPopedomEntities(TreeParam param) {
		return null;
	}

}
