/* 
 * @(#)EisuStudentServiceImpl.java    Created on May 16, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.simple.dao.AbstractStudentDao;
import net.zdsoft.eis.base.simple.service.impl.AbstractStudentServiceImpl;
import net.zdsoft.eisu.base.common.dao.EisuStudentDao;
import net.zdsoft.eisu.base.common.entity.EisuStudent;
import net.zdsoft.eisu.base.common.entity.Specialty;
import net.zdsoft.eisu.base.common.service.EisuStudentService;
import net.zdsoft.eisu.base.common.service.SpecialtyService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 16, 2011 3:06:01 PM $
 */
public class EisuStudentServiceImpl extends
		AbstractStudentServiceImpl<EisuStudent> implements EisuStudentService {
	protected EisuStudentDao eisuStudentDao;
	private SpecialtyService specialtyService;

	public void setEisuStudentDao(EisuStudentDao eisuStudentDao) {
		this.eisuStudentDao = eisuStudentDao;
	}

	public void setSpecialtyService(SpecialtyService specialtyService) {
		this.specialtyService = specialtyService;
	}

	@Override
	public AbstractStudentDao<EisuStudent> getStudentDao() {
		return eisuStudentDao;
	}

	public List<EisuStudent> getStudentsBySpecialtyId(String specialtyId) {
		return eisuStudentDao.getStudentsBySpecialtyId(specialtyId);
	}

	public List<EisuStudent> getStudentsBySpecialtyPointId(String specialtyId,
			String specialtyPointId) {
		return eisuStudentDao.getStudentsBySpecialtyPointId(specialtyId,
				specialtyPointId);
	}

	public List<EisuStudent> getStudentsByFaintnessInstituteId(String schoolId,
			String instituteId, boolean isAll, boolean isContainFreshman,
			String studentName, String studentCode) {
		List<EisuStudent> students = new ArrayList<EisuStudent>();

		String parentId = null;
		int parentType = Specialty.PARENT_SCHOOL;
		// 用户所在部门挂在学校下
		if (BaseConstant.ZERO_GUID.equals(instituteId)) {
			parentId = schoolId;
			parentType = Specialty.PARENT_SCHOOL;
		} else {
			parentId = instituteId;
			parentType = Specialty.PARENT_INSTITUTE;
		}

		String[] specialtyIds = null;
		if (isAll) {
			specialtyIds = specialtyService.getAllSpecialtyIdsByParent(
					parentId, parentType);
		} else {
			specialtyIds = specialtyService.getSpecialtyIdsByParent(parentId,
					parentType);
		}
		if (specialtyIds != null) {
			students = eisuStudentDao.getStudentsBySpecialtyId(specialtyIds,
					isContainFreshman, studentName, studentCode);
		}
		return students;
	}

	public void updateBackGroundByIds(String background, String[] ids) {
		eisuStudentDao.updateBackGroundByIds(background, ids);
	}
}
