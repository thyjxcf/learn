/* 
 * @(#)InstituteTreeXmlAction.java    Created on May 19, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.tree.action.BaseTreeXmlModelAction;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eis.base.tree.service.TreeService;
import net.zdsoft.eisu.base.common.entity.Institute;
import net.zdsoft.eisu.base.common.service.InstituteService;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 19, 2011 2:05:34 PM $
 */
public class InstituteTreeXmlAction extends BaseTreeXmlModelAction {
	private static final long serialVersionUID = -7513403752612122165L;

	private InstituteService instituteService;
	private XLoadTreeItemConverter<Institute> instituteTreeItemConverter;
	private TreeService instituteTree;

	public void setInstituteTree(TreeService instituteTree) {
		this.instituteTree = instituteTree;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public void setInstituteTreeItemConverter(
			XLoadTreeItemConverter<Institute> instituteTreeItemConverter) {
		this.instituteTreeItemConverter = instituteTreeItemConverter;
	}

	protected List<XLoadTreeItem> getInstituteItems() {
		List<XLoadTreeItem> items = new ArrayList<XLoadTreeItem>();

		// 不显示院系
		if (!treeParam.isShowInstitute()) {
			return items;
		}

		// 系节点
		if (EisuTreeConstant.getTreeDepth(treeParam.getLayer()) >= EisuTreeConstant
				.getTreeDepth(EisuTreeConstant.ITEMTYPE_INSTITUTE)) {
			if (treeParam.isNeedPopedom()) {
				Map<String, XLoadTreeItem> treeItemMap = instituteTree
						.getPopedomTreeItemMap(treeParam);
				if (treeItemMap == null) {
					return items;
				}
				XLoadTreeItem item = treeItemMap.get(treeParam.getParentId());
				if (null != item) {// 有下级节点，则进行权限控制
					if (treeParam.getInstituteKind() == 0
							|| item.getItemKind() == treeParam
									.getInstituteKind()) {
						items.add(item);
					}
					return items;
				} else {
					// 直接下属权限
					if (treeParam.isNeedDirectPopedom()
							&& treeParam.getLayer() != EisuTreeConstant.ITEMTYPE_INSTITUTE) {
						return items;
					}
				}
			}

			List<Institute> list = instituteService.getInstitutesByParent(
					treeParam.getParentId(), treeParam.getParentType(),
					treeParam.getInstituteKind(), !treeParam.isShowValid());

			List<String> ids = new ArrayList<String>();
			// 学籍
			if (subsystemPopedomService != null
					&& treeParam.isOnlyShowPopedom()) {
				ids = subsystemPopedomService.getPopedomInstituteIds(treeParam
						.getTeacherId());
				List<Institute> institutes = new ArrayList<Institute>();
				for (Institute ist : list) {
					if (ids.contains(ist.getId()))
						institutes.add(ist);
				}
				list = institutes;
			}
			// 教务
			if (eduadmSubsystemService != null
					&& treeParam.isEduadmShowPopedom()) {
//				if (!eduadmSubsystemService.isEduadmAdmin(
//						treeParam.getUnitId(), treeParam.getTeacherId())) {
//					ids = eduadmSubsystemService.getInstitutetByTeacherId(
//							treeParam.getUnitId(), treeParam.getTeacherId());
//					List<Institute> institutes = new ArrayList<Institute>();
//					for (Institute ist : list) {
//						if (ids.contains(ist.getId()))
//							institutes.add(ist);
//					}
//					list = institutes;
//				}
			}
			List<XLoadTreeItem> _items = instituteTreeItemConverter
					.buildTreeItems(list, treeParam);
			items.addAll(_items);
		}
		return items;
	}

	/**
	 * 取权限Map
	 * 
	 * @param treeParam
	 * @return
	 */
	protected boolean isReturn(TreeParam treeParam) {
		return instituteTree.isReturn(treeParam);
	}

	/**
	 * 点击学校或院系节点，展示系
	 * 
	 * @return
	 * @throws Exception
	 */
	public String expandInstitute() throws Exception {
		List<XLoadTreeItem> items = getInstituteItems();

		return buildTreeXml(items);
	}
}
