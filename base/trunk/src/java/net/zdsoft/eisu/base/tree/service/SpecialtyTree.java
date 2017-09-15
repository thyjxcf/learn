/* 
 * @(#)EisuStudentTree.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.service;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeItemParam;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.Institute;
import net.zdsoft.eisu.base.common.entity.Specialty;
import net.zdsoft.eisu.base.common.service.SpecialtyService;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 4:10:16 PM $
 */
public class SpecialtyTree extends InstituteTree {
	private SpecialtyService specialtyService;
	private XLoadTreeItemConverter<Specialty> specialtyTreeItemConverter;

	public void setSpecialtyService(SpecialtyService specialtyService) {
		this.specialtyService = specialtyService;
	}

	public void setSpecialtyTreeItemConverter(
			XLoadTreeItemConverter<Specialty> specialtyTreeItemConverter) {
		this.specialtyTreeItemConverter = specialtyTreeItemConverter;
	}

	@Override
	protected void fillSchoolChildNodeParam(TreeParam param, TreeItemParam item) {
		item.setXmlSrc("/common/xtree/instituteSpecialtyTreeXml.action");
		item.setItemLinkParams("parentId=" + param.getUnitId() + "&parentType="
				+ Institute.PARENT_SCHOOL);
	}

	@Override
	protected void addSchoolChildNodes(XLoadTreeItem parentNode, TreeParam param) {
		super.addSchoolChildNodes(parentNode, param);

		// 学校子节点：专业
		if (EisuTreeConstant.getTreeDepth(param.getLayer()) >= EisuTreeConstant
				.getTreeDepth(EisuTreeConstant.ITEMTYPE_SPECIALTY)) {

			List<Specialty> specialties = null;

			// 显示院系时，只显示学校的直属专业，否则显示学校下的所有专业
			if (param.isShowInstitute()) {
				if (isReturn(param))
					return;

				specialties = specialtyService.getSpecialtysByParent(
						param.getUnitId(), Specialty.PARENT_SCHOOL,
						!param.isShowValid());
			} else {
				if (param.isNeedPopedom()) {
					specialties = getPopedomEntities(param);
				} else {
					specialties = specialtyService.getAllSpecialtysByParent(
							param.getUnitId(), Specialty.PARENT_SCHOOL,
							!param.isShowValid());
				}
			}

			List<String> ids = new ArrayList<String>();
			// 学籍
			if (subsystemPopedomService != null && param.isOnlyShowPopedom()) {
				ids = subsystemPopedomService.getPopedomSpecialtyIds(param
						.getTeacherId());
			}
			// 教务
			boolean isEduadmAdmin = true;
//			if (eduadmSubsystemService != null && param.isEduadmShowPopedom()) {
//				isEduadmAdmin = eduadmSubsystemService.isEduadmAdmin(
//						param.getUnitId(), param.getTeacherId());
//				if (!isEduadmAdmin) {
//					ids = eduadmSubsystemService.getInstitutetByTeacherId(
//							param.getUnitId(), param.getTeacherId());
//				}
//
//			}

			for (Specialty e : specialties) {
				// 学籍
				if (subsystemPopedomService != null
						&& param.isOnlyShowPopedom()) {
					if (!ids.contains(e.getId()))
						continue;
				}
				// 教务
				if (eduadmSubsystemService != null
						&& param.isEduadmShowPopedom()) {
					if (!isEduadmAdmin) {
						if (!ids.contains(e.getParentId()))
							continue;
					}
				}
				specialtyTreeItemConverter.buildTreeItem(e, param, parentNode,
						makeXTreeItemName());
			}
		}

	}

	@SuppressWarnings("unchecked")
	public List<Specialty> getPopedomEntities(TreeParam param) {
		Teacher teacher = getUserTeacher(param);
		String instituteId = teacher.getInstituteId();
		List<Specialty> specialties = new ArrayList<Specialty>();
		String parentId = null;
		int parentType = Specialty.PARENT_SCHOOL;
		if (StringUtils.isNotBlank(instituteId)) {
			parentId = instituteId;
			parentType = Specialty.PARENT_INSTITUTE;
		} else {
			return specialties;
		}

		if (param.isNeedAllPopedom()) {
			specialties = specialtyService.getAllSpecialtysByParent(parentId,
					parentType, !param.isShowValid());
		} else if (param.isNeedDirectPopedom()) {
			specialties = specialtyService.getSpecialtysByParent(parentId,
					parentType, !param.isShowValid());
		}
		return specialties;
	}

}
