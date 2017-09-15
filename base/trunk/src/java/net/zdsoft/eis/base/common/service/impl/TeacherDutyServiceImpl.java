package net.zdsoft.eis.base.common.service.impl;

import java.util.List;

import net.zdsoft.eis.base.common.dao.TeacherDutyDao;
import net.zdsoft.eis.base.common.entity.TeacherDuty;
import net.zdsoft.eis.base.common.service.TeacherDutyService;

/**
 * @author chens
 * @version 创建时间：2015-2-28 下午4:23:52
 * 
 */
public class TeacherDutyServiceImpl implements TeacherDutyService {

	private TeacherDutyDao teacherDutyDao;
	
	@Override
	public List<TeacherDuty> getTeacherDutyListByTeacherIds(
			String dutyCode, String[] teacherIds) {
		return teacherDutyDao.getTeacherDutyListByTeacherIds(dutyCode, teacherIds);
	}
	
	@Override
	public List<TeacherDuty> getTeacherDutyListByTeacherIds(String[] teacherIds) {
		return teacherDutyDao.getTeacherDutyListByTeacherIds(teacherIds);
	}

	public void setTeacherDutyDao(TeacherDutyDao teacherDutyDao) {
		this.teacherDutyDao = teacherDutyDao;
	}

}
