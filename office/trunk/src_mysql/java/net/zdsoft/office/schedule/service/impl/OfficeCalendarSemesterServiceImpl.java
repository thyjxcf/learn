package net.zdsoft.office.schedule.service.impl;

import java.util.*;
import net.zdsoft.office.schedule.entity.OfficeCalendarSemester;
import net.zdsoft.office.schedule.service.OfficeCalendarSemesterService;
import net.zdsoft.office.schedule.dao.OfficeCalendarSemesterDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_calendar_semester
 * @author 
 * 
 */
public class OfficeCalendarSemesterServiceImpl implements OfficeCalendarSemesterService{
	private OfficeCalendarSemesterDao officeCalendarSemesterDao;

	@Override
	public OfficeCalendarSemester save(OfficeCalendarSemester officeCalendarSemester){
		return officeCalendarSemesterDao.save(officeCalendarSemester);
	}

	@Override
	public Integer delete(String[] ids){
		return officeCalendarSemesterDao.delete(ids);
	}

	@Override
	public Integer update(OfficeCalendarSemester officeCalendarSemester){
		return officeCalendarSemesterDao.update(officeCalendarSemester);
	}
	@Override
	public void updateContent(OfficeCalendarSemester officeCalendarSemester) {
		officeCalendarSemesterDao.updateContent(officeCalendarSemester);
	}
	@Override
	public OfficeCalendarSemester getOfficeCalendarSemesterById(String id){
		return officeCalendarSemesterDao.getOfficeCalendarSemesterById(id);
	}

	@Override
	public Map<String, OfficeCalendarSemester> getOfficeCalendarSemesterMapByIds(String[] ids){
		return officeCalendarSemesterDao.getOfficeCalendarSemesterMapByIds(ids);
	}

	@Override
	public List<OfficeCalendarSemester> getOfficeCalendarSemesterList(){
		return officeCalendarSemesterDao.getOfficeCalendarSemesterList();
	}

	@Override
	public List<OfficeCalendarSemester> getOfficeCalendarSemesterPage(Pagination page){
		return officeCalendarSemesterDao.getOfficeCalendarSemesterPage(page);
	}

	@Override
	public List<OfficeCalendarSemester> getOfficeCalendarSemesterByUnitIdList(String unitId){
		return officeCalendarSemesterDao.getOfficeCalendarSemesterByUnitIdList(unitId);
	}

	@Override
	public List<OfficeCalendarSemester> getOfficeCalendarSemesterByUnitIdPage(String unitId, Pagination page){
		return officeCalendarSemesterDao.getOfficeCalendarSemesterByUnitIdPage(unitId, page);
	}
	
	@Override
	public OfficeCalendarSemester getCalendarSemester(String calyear,
			String unitId) {
		return officeCalendarSemesterDao.getCalendarSemester(calyear, unitId);
	}
	@Override
	public OfficeCalendarSemester getCalendarSemester(String acadyear,
			String semester, String unitId) {
		return officeCalendarSemesterDao.getCalendarSemester(acadyear, semester, unitId);
	}
	public void setOfficeCalendarSemesterDao(OfficeCalendarSemesterDao officeCalendarSemesterDao){
		this.officeCalendarSemesterDao = officeCalendarSemesterDao;
	}
}
