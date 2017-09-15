/* 
 * @(#)EisuClassServiceImpl.java    Created on May 14, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.simple.dao.AbstractClassDao;
import net.zdsoft.eis.base.simple.service.impl.AbstractClassServiceImpl;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eisu.base.common.dao.EisuClassDao;
import net.zdsoft.eisu.base.common.entity.EisuClass;
import net.zdsoft.eisu.base.common.entity.Institute;
import net.zdsoft.eisu.base.common.entity.Specialty;
import net.zdsoft.eisu.base.common.service.EisuClassService;
import net.zdsoft.eisu.base.common.service.InstituteService;
import net.zdsoft.eisu.base.common.service.SpecialtyService;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 14, 2011 12:58:28 PM $
 */
public class EisuClassServiceImpl extends AbstractClassServiceImpl<EisuClass>
		implements EisuClassService {
	protected EisuClassDao eisuClassDao;
	private InstituteService instituteService;
	private SpecialtyService specialtyService;

	public void setEisuClassDao(EisuClassDao eisuClassDao) {
		this.eisuClassDao = eisuClassDao;
	}

	public AbstractClassDao<EisuClass> getClassDao() {
		return eisuClassDao;
	}

	public void updateGraduateSign(String classId, String graduateAcadyear,
			int sign) {
		eisuClassDao.updateGraduateSign(classId, graduateAcadyear, sign);
	}

	public void updatePreGraduateSign(int sign, String... classIds) {
		eisuClassDao.updatePreGraduateSign(sign, classIds);
	}

	public List<EisuClass> getClassesBySpecialtyId(String specialtyId) {
		return eisuClassDao.getClassesBySpecialtyId(specialtyId);
	}

	public List<EisuClass> getClassesBySpecialtyId(String specialtyId,
			String openAcadyear) {
		return eisuClassDao.getClassesBySpecialtyId(specialtyId, openAcadyear);
	}

	public List<EisuClass> getClassesBySpecialtyId(String specialtyId,
			String openAcadyear, String teacherId) {
		return eisuClassDao.getClassesBySpecialtyId(specialtyId, openAcadyear,
				teacherId);
	}

	public List<EisuClass> getClassesBySpecialtyId(String specialtyId,
			boolean isGraduate) {
		return eisuClassDao.getClassesBySpecialtyId(specialtyId, isGraduate);
	}

	public List<EisuClass> getClassesBySpecialtyPointId(String specialtyId,
			String specialtyPointId, String openAcadyear) {
		return eisuClassDao.getClassesBySpecialtyPointId(specialtyId,
				specialtyPointId, openAcadyear);
	}

	public List<EisuClass> getClassesBySpecialtyId(String specialtyId,
			String specialtyPointId, String openAcadyear, boolean isGraduate) {
		return eisuClassDao.getClassesBySpecialtyId(specialtyId,
				specialtyPointId, openAcadyear, isGraduate);
	}

	@Override
	public List<EisuClass> getPreGraudateClasses(String specialtyId,
			String specialtyPointId, String openAcadyear) {
		return eisuClassDao.getPreGraudateClasses(specialtyId,
				specialtyPointId, openAcadyear);
	}

	/**
	 * 根据专业id取班级 包括已毕业的
	 * 
	 * @param specialtyId
	 * @param specialtyPointId
	 * @param openAcadyear
	 * @return
	 */
	public List<EisuClass> getClassesBySpecialty(String specialtyId,
			String specialtyPointId, String openAcadyear) {
		return eisuClassDao.getClassesBySpecialty(specialtyId,
				specialtyPointId, openAcadyear);
	}

	@Override
	public List<EisuClass> getClassesByTeachAreaId(String teachAreaId) {
		return eisuClassDao.getClassesByTeachAreaId(teachAreaId);
	}

	@Override
	public void updateClassState(String[] pointIds) {
		eisuClassDao.updateClassState(pointIds);
	}

	@Override
	public List<EisuClass> getClassesBySpecialtyIds(String[] specialtyId,
			boolean isGraduate) {
		return eisuClassDao.getClassesBySpecialtyIds(specialtyId, isGraduate);
	}

	public List<EisuClass> getClassIdsBySpecIds(String[] specIds,
			String openAcadyear) {
		return eisuClassDao.getClassIdsBySpecIds(specIds, openAcadyear);
	}

	@Override
	public List<EisuClass> getClassesByTeacherId(String teacherId) {
		return eisuClassDao.getClassesByTeacherId(teacherId);
	}
	
	public List<EisuClass> getClassesByLifeGuide(String lifeGuideTeaId){
		return eisuClassDao.getClassesByLifeGuide(lifeGuideTeaId);
	}

	@Override
	public List<String> getClassListByCondition(String id, String type,
			String specId, String pointId) {
		List<String> insIds = new ArrayList<String>();
		List<String> classIds = new ArrayList<String>();
		if (StringUtils.isBlank(type)) {
			return new ArrayList<String>();
		}
		int typeInt = Integer.valueOf(type);
		if (TreeConstant.ITEMTYPE_INSTITUTE == typeInt) {
			List<Institute> list = instituteService.getAllInstitutesByParent(
					id, Institute.PARENT_INSTITUTE, true);
			if (CollectionUtils.isNotEmpty(list)) {
				for (Institute in : list) {
					insIds.add(in.getId());
				}
			}
			String[] specIds = specialtyService.getAllSpecialtyIdsByParent(
					insIds.toArray(new String[0]), Specialty.PARENT_INSTITUTE);
			classIds = eisuClassDao.getClassIdsBySpecIds(specIds);
		} else if (EisuTreeConstant.ITEMTYPE_SPECIALTY == typeInt) {
			List<EisuClass> classList = eisuClassDao
					.getClassesBySpecialtyId(id);
			if (CollectionUtils.isNotEmpty(classList)) {
				for (EisuClass c : classList) {
					classIds.add(c.getId());
				}
			}
		} else if (EisuTreeConstant.ITEMTYPE_SPECIALTY_POINT == typeInt) {
			classIds = eisuClassDao.getClassIdsByPointId(id, specId);
		} else if (String.valueOf(TreeConstant.ITEMTYPE_GRADE).equals(type)) {
			// 如果树上没有专业方向节点 则传过来的值为undefined 需改成空
			if ("undefined".equals(pointId)) {
				pointId = null;
			}
			List<EisuClass> classList = eisuClassDao.getClassesBySpecialtyId(
					specId, pointId, id, false);
			if (CollectionUtils.isNotEmpty(classList)) {
				for (EisuClass c : classList) {
					classIds.add(c.getId());
				}
			}
		} else if (TreeConstant.ITEMTYPE_CLASS == typeInt) {
			classIds.add(id);
		}

		return classIds;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public void setSpecialtyService(SpecialtyService specialtyService) {
		this.specialtyService = specialtyService;
	}

	@Override
	public List<EisuClass> getClassesAllstate(String schoolId) {
		return eisuClassDao.getClassesAllstate(schoolId);
	}

	public List<EisuClass> getClasses(String schoolId) {
		return eisuClassDao.getClasses(schoolId);
	}

	@Override
	public List<EisuClass> getClassByGradeId(String schoolId, String gradeId) {
		return eisuClassDao.getClassByGradeId(schoolId, gradeId);
	}

	@Override
	public List<EisuClass> getClassesByAreaId(String areaId) {
		return eisuClassDao.getClassesByAreaId(areaId);
	}

	@Override
	public List<EisuClass> getClassesByClassIdAndTeacherId(String[] classIds,
			String teacherId) {
		return eisuClassDao
				.getClassesByClassIdAndTeacherId(classIds, teacherId);
	}

	public List<EisuClass> getClassByAcadyearTeaId(String unitId,
			String teachAreaId, String acadyear, String teacherId) {
		return eisuClassDao.getClassByAcadyearTeaId(unitId, teachAreaId,
				acadyear, teacherId);
	}

	public List<EisuClass> getClassByAcadyearTeaId(String unitId,
			String teachAreaId, String acadyear, String teacherId,
			String enrollYear) {
		return eisuClassDao.getClassByAcadyearTeaId(unitId, teachAreaId,
				acadyear, teacherId, enrollYear);
	}
	
	public List<EisuClass> getClassByAcadyear(String unitId,
			String teachAreaId, String acadyear, String teacherId, String enrollYear){
		return eisuClassDao.getClassByAcadyear(unitId, teachAreaId, 
				acadyear, teacherId, enrollYear);
	}

	public List<EisuClass> getClassesByTeachAreaIdAndOpenAcadyear(
			String schoolId, String teachAreaId, String openAcadyear) {
		return eisuClassDao.getClassesByTeachAreaIdAndOpenAcadyear(schoolId,
				teachAreaId, openAcadyear);
	}

	public List<EisuClass> getGraduatingClassesBySpecialty(String specialtyId,
			String specialtyPointId, String graduateAcadyear) {
		return eisuClassDao.getGraduatingClassesBySpecialty(specialtyId,
				specialtyPointId, graduateAcadyear);
	}

	public List<EisuClass> getGraduatingClassesBySpecialty(String specialtyId,
			String specialtyPointId, String graduateAcadyear, String acadyear) {
		return eisuClassDao.getGraduatingClassesBySpecialty(specialtyId,
				specialtyPointId, graduateAcadyear, acadyear);
	}

	public List<EisuClass> getGraduatingClasses(String schoolId,
			String graduateAcadyear) {
		return eisuClassDao.getGraduatingClasses(schoolId, graduateAcadyear);
	}

	public List<EisuClass> getEnrollClasses(String schoolId, String acadyear) {
		return eisuClassDao.getEnrollClasses(schoolId, acadyear);
	}

	@Override
	public Map<String, EisuClass> getClassMap(String schoolId) {
		return eisuClassDao.getClassMap(schoolId);
	}

	@Override
	public Map<String, EisuClass> getClassMapByUnitIdNameCodes(String unitid,
			String[] nameCodes) {
		return eisuClassDao.getClassMapByUnitIdNameCodes(unitid, nameCodes);
	}

	@Override
	public List<EisuClass> getClassesByTeachAreaId(String teachAreaId,
			String openAcadyear) {
		return eisuClassDao.getClassesByTeachAreaId(teachAreaId, openAcadyear);
	}

	public List<EisuClass> getClassesByOpenacadyear(String unitId,
			String openAcadyear, String teacherId) {
		return eisuClassDao.getClassesByOpenacadyear(unitId, openAcadyear,
				teacherId);
	}

}
