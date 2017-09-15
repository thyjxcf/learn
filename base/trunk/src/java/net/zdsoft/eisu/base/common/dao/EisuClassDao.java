/* 
 * @(#)EisuClassDao.java    Created on May 14, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.simple.dao.AbstractClassDao;
import net.zdsoft.eisu.base.common.entity.EisuClass;

/**
 * 班级
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 14, 2011 12:56:39 PM $
 */
public interface EisuClassDao extends AbstractClassDao<EisuClass> {

	/**
	 * 更新班级毕业状态
	 * 
	 * @param classId
	 * @param graduateAcadyear
	 * @param sign
	 */
	public void updateGraduateSign(String classId, String graduateAcadyear,
			int sign);

	/**
	 * 根据专业方向把班级状态改成无效
	 * 
	 * @param specIds
	 */
	public void updateClassState(String[] pointIds);

	/**
	 * 根据专业把班级状态改成无效
	 * 
	 * @param specIds
	 */
	public void updateClassStateBySpec(String[] specIds);

	/**
	 * 更新班级预毕业状态
	 * 
	 * @param sign
	 * @param classIds
	 */
	public void updatePreGraduateSign(int sign, String... classIds);

	/**
	 * 根据专业id取班级
	 * 
	 * @param specialtyId
	 * @return
	 */
	public List<EisuClass> getClassesBySpecialtyId(String specialtyId);

	/**
	 * 根据专业id和开设学年取班级
	 * 
	 * @param specialtyId
	 * @param openAcadyear
	 *            开设学年
	 * @return
	 */
	public List<EisuClass> getClassesBySpecialtyId(String specialtyId,
			String openAcadyear);
	
	/**
	 * 根据专业id和开设学年、教师获取班级
	 * @param specialtyId
	 * @param openAcadyear
	 * @param teacherId
	 * @return
	 */
	public List<EisuClass> getClassesBySpecialtyId(String specialtyId,
			String openAcadyear,String teacherId);

	/**
	 * 根据专业id，是否毕结业取班级
	 * 
	 * @param specialtyId
	 * @param isGraduate
	 * @return
	 */
	public List<EisuClass> getClassesBySpecialtyId(String specialtyId,
			boolean isGraduate);

	/**
	 * 根据专业id和开设学年和专业方向取班级
	 * 
	 * @param specialtyId
	 * @param specialtyPointId
	 *            专业方向id
	 * @param openAcadyear
	 *            开设学年
	 * @return
	 */
	public List<EisuClass> getClassesBySpecialtyPointId(String specialtyId,
			String specialtyPointId, String openAcadyear);

	/**
	 * 根据专业id取班级
	 * 
	 * @param specialtyId
	 * @param specialtyPointId
	 *            专业方向id，可以为空
	 * @param openAcadyear
	 *            开设学年 可以为空
	 * @param isGraduate
	 *            是否毕结业
	 * @return
	 */
	public List<EisuClass> getClassesBySpecialtyId(String specialtyId,
			String specialtyPointId, String openAcadyear, boolean isGraduate);

	/**
	 * 获取预毕业班级
	 * 
	 * @param specialtyId
	 * @param specialtyPointId
	 * @param openAcadyear
	 * @return
	 */
	public List<EisuClass> getPreGraudateClasses(String specialtyId,
			String specialtyPointId, String openAcadyear);

	/**
	 * 根据专业id取班级 包括已毕业的
	 * 
	 * @param specialtyId
	 * @param specialtyPointId
	 * @param openAcadyear
	 * @return
	 */
	public List<EisuClass> getClassesBySpecialty(String specialtyId,
			String specialtyPointId, String openAcadyear);

	/**
	 * 根据校区id取班级
	 * 
	 * @param specialtyId
	 *            为空时，查询没有校区的班级
	 * @return
	 */
	public List<EisuClass> getClassesByTeachAreaId(String teachAreaId);

	/**
	 * 根据专业id数组取班级
	 * 
	 * @param specIds
	 * @return
	 */
	public List<String> getClassIdsBySpecIds(String[] specIds);

	/**
	 * 根据专业方向id取班级
	 * 
	 * @param specIds
	 * @return
	 */
	public List<String> getClassIdsByPointId(String pointId, String specId);

	/**
	 * 根据学校ID得到未毕业的班级列表(包括有效无效)
	 * 
	 * @param schoolId
	 *            学校ID
	 * @return List
	 */
	public List<EisuClass> getClassesAllstate(String schoolId);
	
	/**
	 * 根据年级取班级列表
	 * @param schoolId
	 * @param gradeId
	 * @return
	 */
	public List<EisuClass> getClassByGradeId(String schoolId,String gradeId);

	/**
	 * 根据学校获取正常的班级
	 * 
	 * @param schoolId
	 * @return
	 */
	public List<EisuClass> getClasses(String schoolId);

	/**
	 * 根据校区获取正常的班级
	 * 
	 * @param areaId
	 * @return
	 */
	public List<EisuClass> getClassesByAreaId(String areaId);

	/**
	 * 根据专业id，是否毕结业取班级
	 * 
	 * @param specialtyId
	 * @param isGraduate
	 * @return
	 */
	public List<EisuClass> getClassesBySpecialtyIds(String[] specialtyId,
			boolean isGraduate);

	public List<EisuClass> getClassIdsBySpecIds(String[] specIds,
			String openAcadyear);

	/**
	 * 根据teacherId取班级信息（判断是否班主任）
	 * */
	public List<EisuClass> getClassesByTeacherId(String teacherId);
	
	/**
	 * 根据teacherId取班级信息（判断是否生活指导老师）
	 * */
	public List<EisuClass> getClassesByLifeGuide(String lifeGuideTeaId);

	/**
	 * 根据classId和teacherId取班级信息（判断是否班主任）
	 * 
	 * @param teacherId
	 * @return
	 */
	public List<EisuClass> getClassesByClassIdAndTeacherId(String[] ClassIds,
			String teacherId);

	/**
	 * 根据学校，专业、专业方向、毕业学年得到毕业班列表（即：指定学年内的毕业班列表，不论毕业标志是0还是1）
	 * 
	 * @param specialtyId
	 * @param specialtyPointId
	 * @param graduateAcadyear
	 * @return
	 */
	public List<EisuClass> getGraduatingClassesBySpecialty(String specialtyId,
			String specialtyPointId, String graduateAcadyear);

	/**
	 * 根据学校，专业、专业方向、毕业学年得到毕业班列表（即：指定学年内毕业并且指定入学学年内的毕业班列表，不论毕业标志是0还是1）
	 * 
	 * @param specialtyId
	 * @param specialtyPointId
	 * @param graduateAcadyear
	 * @param acadyear
	 *            开设学年（年级）
	 * @return
	 */
	public List<EisuClass> getGraduatingClassesBySpecialty(String specialtyId,
			String specialtyPointId, String graduateAcadyear, String acadyear);

	/**
	 * 根据学校，毕业学年得到毕业班列表（即：指定学年内的毕业班列表，不论毕业标志是0还是1）
	 * 
	 * @param schoolId
	 * @param graduateAcadyear
	 * @return
	 */
	public List<EisuClass> getGraduatingClasses(String schoolId,
			String graduateAcadyear);

	/**
	 * 不包括预毕业班
	 * 
	 * @param unitId
	 * @param teachAreaId
	 *            校区id
	 * @param acadyear
	 * @param teacherId
	 *            可为空
	 * @return
	 */
	public List<EisuClass> getClassByAcadyearTeaId(String unitId,
			String teachAreaId, String acadyear, String teacherId);
	
	/**
	 * 包括预毕业班
	 * 
	 * @param unitId
	 * @param teachAreaId
	 *            校区id
	 * @param acadyear
	 * @param teacherId
	 *            可为空
	 * @return
	 */
	public List<EisuClass> getClassByAcadyearTeacherId(String unitId,
			String teachAreaId, String acadyear, String teacherId);

	/**
	 * 不包括预毕业班
	 * 
	 * @param unitId
	 * @param teachAreaId
	 *            校区id，可为空
	 * @param acadyear，查询学年下非毕业班级
	 * @param teacherId
	 *            可为空
	 * @param enrollYear 入学学年，可为空
	 * @return
	 */
	public List<EisuClass> getClassByAcadyearTeaId(String unitId,
			String teachAreaId, String acadyear, String teacherId, String enrollYear);
	
	/**
	 * 查询学校班级
	 * @param unitId
	 * @param teachAreaId
	 *            校区id，可为空
	 * @param acadyear，查询学年下班级
	 * @param teacherId
	 *            可为空
	 * @param enrollYear 入学学年，可为空
	 * @return
	 */
	public List<EisuClass> getClassByAcadyear(String unitId,
			String teachAreaId, String acadyear, String teacherId, String enrollYear);
	
	/**
	 * 根据学校和当前学年学期获取当前入学的班级
	 * 
	 * @param schoolId
	 * @param acadyear
	 * @return
	 */
	public List<EisuClass> getEnrollClasses(String schoolId, String acadyear);

	public List<EisuClass> getClassesByTeachAreaIdAndOpenAcadyear(
			String schoolId, String teachAreaId, String openAcadyear);

	/**
	 * 根据学校获取班级map
	 * 
	 * @param schoolId
	 * @return
	 */
	public Map<String, EisuClass> getClassMap(String schoolId);

	/**
	 * 根据学校id,班级名称code获取班级map{key=name+code，value=ent}
	 * 
	 * @param
	 * @return
	 */
	public Map<String, EisuClass> getClassMapByUnitIdNameCodes(String unitid,
			String[] nameCodes);
	/**
	 * 根据校区id 和开设学年
	 * @param teachAreaId
	 * @param openAcadyear
	 * @return
	 */
	public List<EisuClass> getClassesByTeachAreaId(String teachAreaId,
			String openAcadyear);
	
	
	/**
	 * 根据开设学年
	 * @param openAcadyear
	 * @return
	 */
	public List<EisuClass> getClassesByOpenacadyear(String openAcadyear);
	
	/**
	 * 根据开设学年和教师获取班级列表
	 * @param unitId
	 * @param openAcadyear
	 * @param teacherId
	 * @return
	 */
	public List<EisuClass> getClassesByOpenacadyear(String unitId,
			String openAcadyear, String teacherId);
	
}
