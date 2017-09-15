/* 
 * @(#)BaseTreeModelAction.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.action;

import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eis.base.tree.service.TreeService;
import net.zdsoft.leadin.tree.XLoadTreeItem;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 3:31:55 PM $
 */
public abstract class BaseTreeModelAction extends BaseTreeAction implements
		ModelDriven<TreeParam> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1392422607659937016L;

	protected TreeParam treeParam = new TreeParam();

	@Override
	public TreeParam getModel() {
		
		if (getLoginInfo() != null) {
			if (null == treeParam.getUnitId()) {
				treeParam.setUnitId(getLoginInfo().getUnitID());
			}
			treeParam.setUserId(getLoginInfo().getUser().getId());
			treeParam.setUserDeptId(getLoginInfo().getUser().getDeptid());
			treeParam.setTeacherId(getLoginInfo().getUser().getTeacherid());
		}
		
		
		if (null == treeParam.getParentId()) {
			treeParam.setParentId(treeParam.getUnitId());
		}

		treeParam.setContextPath(getContextPath());

		return treeParam;
	}

	/**
	 * 取eduTree
	 * 
	 * @return
	 */
	protected abstract TreeService getTreeService();

//	/**
//	 * 创建树
//	 */
//	protected String buildTree() {
//		treeJSCode = getTreeService().getTree(treeParam).getJScript();
//		return COMMON_TREE;
//	}

	/**
	 * 创建树
	 */
	protected String buildTree() {
		XLoadTreeItem root = getTreeService().getTree(treeParam);		
		disposeObjects(root);
		
		return COMMON_TREE_2;
	}
	
	/**
	 * 自定义树
	 * 
	 * @return
	 * @throws Exception
	 */
	public String customTree() throws Exception {
		if (treeParam.getLayer() == 0) {
			promptMessageDto.setPromptMessage("树层次不能为空！");
			return PROMPTMSG;
		}
		return buildTree();
	}

	public TreeParam getTreeParam() {
		return treeParam;
	}

	public void setTreeParam(TreeParam treeParam) {
		this.treeParam = treeParam;
	}
}
