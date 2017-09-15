/* 
 * @(#)EisuGradeServiceImpl.java    Created on May 18, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.dao.EisuGradeDao;
import net.zdsoft.eisu.base.common.entity.EisuGrade;
import net.zdsoft.eisu.base.common.service.EisuGradeService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 18, 2011 11:25:24 AM $
 */
public class EisuGradeServiceImpl implements EisuGradeService {
	private EisuGradeDao eisuGradeDao;

	public void setEisuGradeDao(EisuGradeDao eisuGradeDao) {
		this.eisuGradeDao = eisuGradeDao;
	}

	public EisuGrade getGrade(String gradeId) {
		return eisuGradeDao.getGrade(gradeId);
	}

	public EisuGrade getGrade(String schoolId, String openAcadyear) {
		return eisuGradeDao.getGrade(schoolId, openAcadyear);
	}

	public List<EisuGrade> getGrades(String schoolId) {
		return eisuGradeDao.getGrades(schoolId);
	}

	public List<EisuGrade> getActiveGrades(String schoolId) {
		return eisuGradeDao.getActiveGrades(schoolId);
	}

	@Override
	public Map<String, EisuGrade> getGradesMap(String schoolId) {
		return eisuGradeDao.getGradesMap(schoolId);
	}

}
