package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.apache.commons.lang.StringUtils;


import net.zdsoft.eis.base.common.dao.GradeDao;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.util.ClassNameFactory;

public class GradeServiceImpl extends Observable implements GradeService {
	private GradeDao gradeDao;
	protected SemesterService semesterService;
	private BasicClassService basicClassService;

	public void setGradeDao(GradeDao gradeDao) {
		this.gradeDao = gradeDao;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public void setSemesterService(SemesterService semesterService) {
		this.semesterService = semesterService;
	}

	// ====================以上是set=============================

	public void updateGraduate(String schId, String acadYear, int section,
			int schoolingLength) {
		gradeDao.updateGraduate(schId, acadYear, section, schoolingLength);
	}

	public Grade getGrade(String gradeId) {
		Grade grade = gradeDao.getGrade(gradeId);
		//ClassNameFactory.getInstance().setGradeDyn(grade);		
		return grade;
	}
	
	public List<Grade> getGradesByIds(String[] gradeIds){
		return gradeDao.getGradesByIds(gradeIds);
	}

	public Grade getGrade(String gradeId,String acadyear){
	    Grade grade = gradeDao.getGrade(gradeId);
	    //ClassNameFactory.getInstance().setGradeDyn(grade, acadyear);
        return grade;
	}
	
	public Grade getGradeByClassId(String classId) {
		BasicClass basicClass = basicClassService.getClass(classId);
		Grade grade = getGrade(basicClass.getSchid(), basicClass.getAcadyear(),
				basicClass.getSection());
		return grade;
	}

	public Grade getGrade(String schoolId, String acadyear, int section) {
		Grade grade = gradeDao.getGrade(schoolId, acadyear, section);
		if (grade == null){
			ClassNameFactory.getInstance().setGradeDyn(grade);
		}
		return grade;
	}

	public String getGradeName(String schoolId, String acadyear, int section,
			int schoolingLength) {
		int grade = 0;
		String currentAcadyear = semesterService.getCurrentAcadyear();
		int endyear = Integer.parseInt(currentAcadyear.substring(5));
		int startyear = Integer.parseInt(acadyear.substring(0, 4));
		grade = endyear - startyear;

		if (grade > schoolingLength) {
			return "";
		}
		Grade mygrade=gradeDao.getGrade(schoolId, currentAcadyear, section);
		if (mygrade!=null&&StringUtils.isNotBlank(mygrade.getGradename())) {
			return mygrade.getGradename();
		}
		return "";

/*		String gradeName = ClassNameFactory.getInstance().getGradeNameDyn(section,
				grade);*/
		/*if (null == gradeName)
			gradeName = "";
		return gradeName;*/

	}

	public List<Grade> getGrades(String schoolId) {
		List<Grade> listOfEntity = gradeDao.getGrades(schoolId);
		return listOfEntity;
		//return setGradeNameDyn(listOfEntity);
	}

	public List<Grade> getUsingGrades(String schoolId, String acadyear) {
		List<Grade> listOfEntity = gradeDao.getGrades(schoolId);
		List<BasicClass> listOfBasicClass = basicClassService
				.getClasses(schoolId);
		Map<String, String> mapOfClassSpecialKey = new HashMap<String, String>();
		String key;
		for (BasicClass basicClass : listOfBasicClass) {
			key = basicClass.getSchid() + basicClass.getAcadyear()
					+ String.valueOf(basicClass.getSection())
					+ basicClass.getSchoolinglen();
			mapOfClassSpecialKey.put(key, basicClass.getId());
		}
		List<Grade> listOfBasicGrade = new ArrayList<Grade>();
		for (Grade basicGrade : listOfEntity) {
			key = basicGrade.getSchid() + basicGrade.getAcadyear()
					+ String.valueOf(basicGrade.getSection())
					+ basicGrade.getSchoolinglen();
			if (mapOfClassSpecialKey.get(key) != null) {
				listOfBasicGrade.add(basicGrade);
			}
		}
		return listOfBasicGrade;
/*		if (listOfBasicGrade.size() == 0)
			return listOfBasicGrade;
		
		return ClassNameFactory.getInstance().buildGradeDyn(
		        listOfBasicGrade.get(0).getSchid(), listOfBasicGrade,acadyear);*/
	}

	public List<Grade> getGrades(String schoolId, int section) {
		List<Grade> listOfEntity = gradeDao.getGrades(schoolId, section);
		return listOfEntity;
		//return setGradeNameDyn(listOfEntity);
	}

	public List<Grade> getGradesByTeacherId(String teacherId) {
		List<Grade> listOfEntity = gradeDao.getGradesByTeacherId(teacherId);
		return listOfEntity;/*
		if (listOfEntity.size() == 0)
			return listOfEntity;
		return setGradeNameDyn(listOfEntity);*/
	}

/*	private List<Grade> setGradeNameDyn(List<Grade> gradeList) {
	    if (gradeList.size() == 0)
            return gradeList;
	    
		return ClassNameFactory.getInstance().buildGradeDyn(
				gradeList.get(0).getSchid(), gradeList);
	}*/

	@Override
	public Map<String, Grade> getGradeMapBySchid(String schId) {
		return gradeDao.getGradeMapBySchid(schId);
	}
	public List<Grade> getBaseGradesBySchidSectionAcadyear(String schoolId, String acadyear, Integer[] section) {
		List<Grade> listOfEntity = gradeDao.getBaseGradesBySchidSectionAcadyear(schoolId, acadyear, section);
		String curAcadyear = semesterService.getCurrentAcadyear();
		for (Grade grade : listOfEntity) {	
			ClassNameFactory.getInstance().setGradeDyn(grade, curAcadyear);
		}
		return listOfEntity;
	}
}
