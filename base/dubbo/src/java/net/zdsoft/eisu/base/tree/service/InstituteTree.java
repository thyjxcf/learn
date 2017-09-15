/* 
 * @(#)EisuStudentTree.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.subsystemcall.service.EduadmSubsystemService;
import net.zdsoft.eis.base.subsystemcall.service.SubsystemPopedomService;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeItemParam;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eis.base.tree.service.EduTree;
import net.zdsoft.eisu.base.common.entity.Institute;
import net.zdsoft.eisu.base.common.service.InstituteService;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;

/**
 * 院系树
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 4:10:16 PM $
 */
public class InstituteTree extends EduTree {
	private InstituteService instituteService;
	private XLoadTreeItemConverter<Institute> instituteTreeItemConverter;
	protected DeptService deptService;
	protected TeacherService teacherService;
	protected SubsystemPopedomService subsystemPopedomService;
	protected EduadmSubsystemService eduadmSubsystemService;

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public void setInstituteTreeItemConverter(
			XLoadTreeItemConverter<Institute> instituteTreeItemConverter) {
		this.instituteTreeItemConverter = instituteTreeItemConverter;
	}

	public void setEduadmSubsystemService(
			EduadmSubsystemService eduadmSubsystemService) {
		this.eduadmSubsystemService = eduadmSubsystemService;
	}

	@Override
	protected void fillSchoolChildNodeParam(TreeParam param, TreeItemParam item) {
		item.setXmlSrc("/common/xtree/instituteTreeXml.action");
		item.setItemLinkParams("parentId=" + param.getUnitId() + "&parentType="
				+ Institute.PARENT_SCHOOL);
	}

	@Override
	protected void addSchoolChildNodes(XLoadTreeItem parentNode, TreeParam param) {
		// 不显示院系
		if (!param.isShowInstitute()) {
			return;
		}

		// 学校子节点：院系
		if (EisuTreeConstant.getTreeDepth(param.getLayer()) >= EisuTreeConstant
				.getTreeDepth(EisuTreeConstant.ITEMTYPE_INSTITUTE)) {

			// 权限控制
			if (param.isNeedPopedom()) {
				Map<String, XLoadTreeItem> treeItemMap = getPopedomTreeItemMap(param);
				if (treeItemMap == null)
					return;
				XLoadTreeItem item = treeItemMap.get(param.getUnitId());
				if (null != item) {// 有下级节点，则进行权限控制
					if (param.getInstituteKind() == 0
							|| item.getItemKind() == param.getInstituteKind()) {
						instituteTreeItemConverter.buildTreeItem(item,
								parentNode, makeXTreeItemName());
					}
					return;
				} else {
					// 直接下属权限
					if (param.isNeedDirectPopedom()
							&& param.getLayer() != EisuTreeConstant.ITEMTYPE_INSTITUTE) {
						return;
					}
				}
			}

			List<Institute> institutes = instituteService
					.getInstitutesByParent(param.getUnitId(),
							Institute.PARENT_SCHOOL, param.getInstituteKind(),
							!param.isShowValid());
			List<String> ids = new ArrayList<String>();
			if (subsystemPopedomService != null && param.isOnlyShowPopedom()) {
				// 学籍
				ids = subsystemPopedomService.getPopedomInstituteIds(param
						.getTeacherId());
				for (Institute e : institutes) {
					if (subsystemPopedomService != null
							&& param.isOnlyShowPopedom()) {
						if (!ids.contains(e.getId()))
							continue;
					}
					instituteTreeItemConverter.buildTreeItem(e, param,
							parentNode, makeXTreeItemName());
				}
			} else if (eduadmSubsystemService != null
					&& param.isEduadmShowPopedom()) {
				// 教务
//				if (!eduadmSubsystemService.isEduadmAdmin(param.getUnitId(),
//						param.getTeacherId())) {
//					ids = eduadmSubsystemService.getInstitutetByTeacherId(
//							param.getUnitId(), param.getTeacherId());
//					for (Institute e : institutes) {
//						if (eduadmSubsystemService != null
//								&& param.isEduadmShowPopedom()) {
//							if (!ids.contains(e.getId()))
//								continue;
//						}
//						instituteTreeItemConverter.buildTreeItem(e, param,
//								parentNode, makeXTreeItemName());
//					}
//				} else {
//					for (Institute e : institutes) {
//						instituteTreeItemConverter.buildTreeItem(e, param,
//								parentNode, makeXTreeItemName());
//					}
//				}

			} else {
				for (Institute e : institutes) {
					instituteTreeItemConverter.buildTreeItem(e, param,
							parentNode, makeXTreeItemName());
				}
			}

		}

	}

	/**
	 * 取教师信息
	 * 
	 * @param treeParam
	 * @return
	 */
	protected Teacher getUserTeacher(TreeParam treeParam) {
		return teacherService.getTeacher(treeParam.getTeacherId());
	}

	/**
	 * 取权限Map
	 * 
	 * @param treeParam
	 */
	public Map<String, XLoadTreeItem> getPopedomTreeItemMap(TreeParam treeParam) {
		Map<String, XLoadTreeItem> popedomTreeItemMap = new HashMap<String, XLoadTreeItem>();
		if (treeParam.isNeedPopedom()) {
			Teacher teacher = getUserTeacher(treeParam);
			if (null == teacher) {
				return popedomTreeItemMap;
			}
			String instituteId = teacher.getInstituteId();
			// 部门挂在学校下
			if (StringUtils.isBlank(instituteId)) {
				return null;
			}
			if (!Dept.TOP_GROUP_GUID.equals(instituteId)) {
				// 部门挂在院系下
				Institute institute = instituteService
						.getInstitute(instituteId);
				XLoadTreeItem item = instituteTreeItemConverter.buildTreeItem(
						institute, treeParam);
				popedomTreeItemMap.put(institute.getParentId(), item);
				while (Institute.PARENT_SCHOOL != institute.getParentType()) {
					institute = instituteService.getInstitute(institute
							.getParentId());
					item = instituteTreeItemConverter.buildTreeItem(institute,
							treeParam);
					popedomTreeItemMap.put(institute.getParentId(), item);
				}
			}
		}
		return popedomTreeItemMap;
	}

	public void wrapTreeParam(TreeParam param) {
		super.wrapTreeParam(param);

		if (param.isShowInstituteCustom())
			return;

		if (EisuTreeConstant.getTreeDepth(param.getLayer()) > EisuTreeConstant
				.getTreeDepth(EisuTreeConstant.ITEMTYPE_INSTITUTE)
				|| (param.getLayer() == EisuTreeConstant.ITEMTYPE_INSTITUTE && param
						.getCustomLayer() != 0)) {
			param.setShowInstitute(unitIniService.getUnitOptionBooleanValue(
					param.getUnitId(), "SYSTEM.TREE.SHOW.INSTITUTE.SWITCH"));
		}
	}

	public void setSubsystemPopedomService(
			SubsystemPopedomService subsystemPopedomService) {
		this.subsystemPopedomService = subsystemPopedomService;
	}
}
