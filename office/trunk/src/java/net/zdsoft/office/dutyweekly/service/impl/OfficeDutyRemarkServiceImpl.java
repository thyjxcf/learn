package net.zdsoft.office.dutyweekly.service.impl;

import java.util.*;

import net.zdsoft.office.dutyweekly.entity.OfficeDutyRemark;
import net.zdsoft.office.dutyweekly.service.OfficeDutyRemarkService;
import net.zdsoft.office.dutyweekly.dao.OfficeDutyRemarkDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_remark
 * @author 
 * 
 */
public class OfficeDutyRemarkServiceImpl implements OfficeDutyRemarkService{
	private OfficeDutyRemarkDao officeDutyRemarkDao;

	@Override
	public OfficeDutyRemark save(OfficeDutyRemark officeDutyRemark){
		return officeDutyRemarkDao.save(officeDutyRemark);
	}

	@Override
	public Integer delete(String[] ids){
		return officeDutyRemarkDao.delete(ids);
	}

	@Override
	public Integer update(OfficeDutyRemark officeDutyRemark){
		return officeDutyRemarkDao.update(officeDutyRemark);
	}

	@Override
	public OfficeDutyRemark getOfficeDutyRemarkById(String id){
		return officeDutyRemarkDao.getOfficeDutyRemarkById(id);
	}

	@Override
	public Map<String, OfficeDutyRemark> getOfficeDutyRemarkMapByIds(String[] ids){
		return officeDutyRemarkDao.getOfficeDutyRemarkMapByIds(ids);
	}

	@Override
	public List<OfficeDutyRemark> getOfficeDutyRemarkList(){
		return officeDutyRemarkDao.getOfficeDutyRemarkList();
	}

	@Override
	public List<OfficeDutyRemark> getOfficeDutyRemarkPage(Pagination page){
		return officeDutyRemarkDao.getOfficeDutyRemarkPage(page);
	}

	@Override
	public List<OfficeDutyRemark> getOfficeDutyRemarkByUnitIdList(String unitId){
		return officeDutyRemarkDao.getOfficeDutyRemarkByUnitIdList(unitId);
	}

	@Override
	public List<OfficeDutyRemark> getOfficeDutyRemarkByUnitIdPage(String unitId, Pagination page){
		return officeDutyRemarkDao.getOfficeDutyRemarkByUnitIdPage(unitId, page);
	}

	@Override
	public void deleteOfficeDutyRemark(String unitId, String worklyId,
			Date dutyDate) {
		officeDutyRemarkDao.deleteOfficeDutyRemark(unitId, worklyId, dutyDate);
	}

	@Override
	public List<OfficeDutyRemark> getOfficeDutyRemarksAndUnitIdAndMore(
			String unitId, String worklyId, Date[] dutyDate) {
		return officeDutyRemarkDao.getOfficeDutyRemarksAndUnitIdAndMore(unitId, worklyId, dutyDate);
	}

	@Override
	public OfficeDutyRemark getOfficeDutyRemarkByUnitIdAndOthers(String unitId,
			String worklyId, Date dutyDate) {
		return officeDutyRemarkDao.getOfficeDutyRemarkByUnitIdAndOthers(unitId, worklyId, dutyDate);
	}

	public void setOfficeDutyRemarkDao(OfficeDutyRemarkDao officeDutyRemarkDao){
		this.officeDutyRemarkDao = officeDutyRemarkDao;
	}
}
