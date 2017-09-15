package net.zdsoft.office.dutyweekly.service.impl;

import java.util.*;

import net.zdsoft.office.dutyweekly.entity.OfficeDutyProject;
import net.zdsoft.office.dutyweekly.service.OfficeDutyProjectService;
import net.zdsoft.office.dutyweekly.dao.OfficeDutyProjectDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_project
 * @author 
 * 
 */
public class OfficeDutyProjectServiceImpl implements OfficeDutyProjectService{
	private OfficeDutyProjectDao officeDutyProjectDao;

	@Override
	public OfficeDutyProject save(OfficeDutyProject officeDutyProject){
		return officeDutyProjectDao.save(officeDutyProject);
	}

	@Override
	public Integer delete(String[] ids){
		return officeDutyProjectDao.delete(ids);
	}

	@Override
	public Integer update(OfficeDutyProject officeDutyProject){
		return officeDutyProjectDao.update(officeDutyProject);
	}

	@Override
	public OfficeDutyProject getOfficeDutyProjectById(String id){
		return officeDutyProjectDao.getOfficeDutyProjectById(id);
	}

	@Override
	public Map<String, OfficeDutyProject> getOfficeDutyProjectMapByIds(String[] ids){
		return officeDutyProjectDao.getOfficeDutyProjectMapByIds(ids);
	}

	@Override
	public List<OfficeDutyProject> getOfficeDutyProjectList(){
		return officeDutyProjectDao.getOfficeDutyProjectList();
	}

	@Override
	public List<OfficeDutyProject> getOfficeDutyProjectPage(Pagination page){
		return officeDutyProjectDao.getOfficeDutyProjectPage(page);
	}

	@Override
	public List<OfficeDutyProject> getOfficeDutyProjectByUnitIdList(String unitId){
		return officeDutyProjectDao.getOfficeDutyProjectByUnitIdList(unitId);
	}

	@Override
	public List<OfficeDutyProject> getOfficeDutyProjectByUnitIdListss(
			String unitId, String years, String semesters) {
		return officeDutyProjectDao.getOfficeDutyProjectByUnitIdListss(unitId, years, semesters);
	}

	@Override
	public List<OfficeDutyProject> getOfficeDutyProjectByUnitIdPage(String unitId, Pagination page){
		return officeDutyProjectDao.getOfficeDutyProjectByUnitIdPage(unitId, page);
	}

	public void setOfficeDutyProjectDao(OfficeDutyProjectDao officeDutyProjectDao){
		this.officeDutyProjectDao = officeDutyProjectDao;
	}
}
