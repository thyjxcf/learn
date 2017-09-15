package net.zdsoft.office.dailyoffice.service.impl;

import java.util.*;

import net.zdsoft.office.dailyoffice.entity.OfficeMeetingAudit;
import net.zdsoft.office.dailyoffice.service.OfficeMeetingAuditService;
import net.zdsoft.office.dailyoffice.dao.OfficeMeetingAuditDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_meeting_audit
 * @author 
 * 
 */
public class OfficeMeetingAuditServiceImpl implements OfficeMeetingAuditService{
	private OfficeMeetingAuditDao officeMeetingAuditDao;

	@Override
	public OfficeMeetingAudit save(OfficeMeetingAudit officeMeetingAudit){
		return officeMeetingAuditDao.save(officeMeetingAudit);
	}

	@Override
	public Integer delete(String[] ids){
		return officeMeetingAuditDao.delete(ids);
	}

	@Override
	public Integer update(OfficeMeetingAudit officeMeetingAudit){
		return officeMeetingAuditDao.update(officeMeetingAudit);
	}

	@Override
	public OfficeMeetingAudit getOfficeMeetingAuditById(String id){
		return officeMeetingAuditDao.getOfficeMeetingAuditById(id);
	}

	@Override
	public Map<String, OfficeMeetingAudit> getOfficeMeetingAuditMapByIds(String[] ids){
		return officeMeetingAuditDao.getOfficeMeetingAuditMapByIds(ids);
	}

	@Override
	public List<OfficeMeetingAudit> getOfficeMeetingAuditList(){
		return officeMeetingAuditDao.getOfficeMeetingAuditList();
	}

	@Override
	public List<OfficeMeetingAudit> getOfficeMeetingAuditPage(Pagination page){
		return officeMeetingAuditDao.getOfficeMeetingAuditPage(page);
	}

	@Override
	public List<OfficeMeetingAudit> getOfficeMeetingAuditByUnitIdList(String unitId){
		return officeMeetingAuditDao.getOfficeMeetingAuditByUnitIdList(unitId);
	}

	@Override
	public List<OfficeMeetingAudit> getOfficeMeetingAuditByUnitIdPage(String unitId, Pagination page){
		return officeMeetingAuditDao.getOfficeMeetingAuditByUnitIdPage(unitId, page);
	}

	@Override
	public OfficeMeetingAudit getOfficeMeetingAuditByApplyId(String applyId){
		List<OfficeMeetingAudit> meetingAuditList = officeMeetingAuditDao.getOfficeMeetingAuditByApplyId(applyId);
		if(meetingAuditList.size()>0){
			return meetingAuditList.get(0);
		}else{
			return new OfficeMeetingAudit();
		}
	}
	
	@Override
	public Map<String, OfficeMeetingAudit> getOfficeMeetingAuditMapByApplyIds(String[] applyIds){
		Map<String, List<OfficeMeetingAudit>> auditMap = officeMeetingAuditDao
				.getOfficeMeetingAuditMapByApplyIds(applyIds);
		Map<String, OfficeMeetingAudit> meetingAuditMap = new HashMap<String, OfficeMeetingAudit>();
		for(Map.Entry<String, List<OfficeMeetingAudit>> entry : auditMap.entrySet()){
			OfficeMeetingAudit meetingAudit = entry.getValue().get(0);
			meetingAuditMap.put(entry.getKey(), meetingAudit);
		}
		return meetingAuditMap;
	}
	
	public void setOfficeMeetingAuditDao(OfficeMeetingAuditDao officeMeetingAuditDao){
		this.officeMeetingAuditDao = officeMeetingAuditDao;
	}
}
	