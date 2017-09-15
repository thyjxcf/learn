package net.zdsoft.office.teacherAttendance.service.impl;


import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendanceSetDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceSet;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceSetService;
/**
 * office_attendance_set(考勤制度设置)
 * @author 
 * 
 */
public class OfficeAttendanceSetServiceImpl implements OfficeAttendanceSetService{
	private OfficeAttendanceSetDao officeAttendanceSetDao;

	@Override
	public OfficeAttendanceSet save(OfficeAttendanceSet officeAttendanceSet){
		return officeAttendanceSetDao.save(officeAttendanceSet);
	}

	@Override
	public Integer delete(String[] ids){
		return officeAttendanceSetDao.delete(ids);
	}

	@Override
	public Integer update(OfficeAttendanceSet officeAttendanceSet){
		return officeAttendanceSetDao.update(officeAttendanceSet);
	}

	@Override
	public OfficeAttendanceSet getOfficeAttendanceSetById(String id){
		return officeAttendanceSetDao.getOfficeAttendanceSetById(id);
	}
	@Override
	public List<OfficeAttendanceSet> getOfficeAttendanceSetByUnitId(String unitId) {
		// TODO Auto-generated method stub
		return officeAttendanceSetDao.getOfficeAttendanceSetByUnitId(unitId);
	}

	public void setOfficeAttendanceSetDao(
			OfficeAttendanceSetDao officeAttendanceSetDao) {
		this.officeAttendanceSetDao = officeAttendanceSetDao;
	}

	@Override
	public Map<String, OfficeAttendanceSet> getOfficeAttendanceSetMapByUnitId(
			String unitId) {
		// TODO Auto-generated method stub
		return officeAttendanceSetDao.getOfficeAttendanceSetMapByUnitId(unitId);
	}
	
}