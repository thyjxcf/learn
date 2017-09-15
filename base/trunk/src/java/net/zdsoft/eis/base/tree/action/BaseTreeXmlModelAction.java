/* 
 * @(#)BaseTreeXmlModelAction.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.action;

import com.opensymphony.xwork2.ModelDriven;

import net.zdsoft.eis.base.subsystemcall.service.EduadmSubsystemService;
import net.zdsoft.eis.base.subsystemcall.service.SubsystemPopedomService;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eis.base.tree.service.TreeService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 3:34:45 PM $
 */
public class BaseTreeXmlModelAction extends BaseTreeXmlAction implements
		ModelDriven<TreeParam> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1867293502301154004L;

	protected TreeParam treeParam = new TreeParam();
	protected SubsystemPopedomService subsystemPopedomService;
	protected EduadmSubsystemService eduadmSubsystemService;

	@Override
	public TreeParam getModel() {
		treeParam.setContextPath(getContextPath());
		return treeParam;
	}

	/**
	 * 取eduTree
	 * 
	 * @return
	 */
	protected TreeService getTreeService() {
		return null;
	}

	public TreeParam getTreeParam() {
		return treeParam;
	}

	public void setTreeParam(TreeParam treeParam) {
		this.treeParam = treeParam;
	}

	public void setSubsystemPopedomService(
			SubsystemPopedomService subsystemPopedomService) {
		this.subsystemPopedomService = subsystemPopedomService;
	}

	public void setEduadmSubsystemService(
			EduadmSubsystemService eduadmSubsystemService) {
		this.eduadmSubsystemService = eduadmSubsystemService;
	}

}
