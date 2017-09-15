package net.zdsoft.office.survey.service.impl;

import java.util.*;

import net.zdsoft.office.survey.entity.OfficeSurveyApply;
import net.zdsoft.office.survey.entity.OfficeSurveyAudit;
import net.zdsoft.office.survey.service.OfficeSurveyAuditService;
import net.zdsoft.office.survey.dao.OfficeSurveyApplyDao;
import net.zdsoft.office.survey.dao.OfficeSurveyAuditDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_survey_audit
 * @author 
 * 
 */
public class OfficeSurveyAuditServiceImpl implements OfficeSurveyAuditService{
	private OfficeSurveyAuditDao officeSurveyAuditDao;
	private OfficeSurveyApplyDao officeSurveyApplyDao;

	@Override
	public OfficeSurveyAudit save(OfficeSurveyAudit officeSurveyAudit){
		return officeSurveyAuditDao.save(officeSurveyAudit);
	}

	@Override
	public Integer delete(String[] ids){
		return officeSurveyAuditDao.delete(ids);
	}

	@Override
	public Integer update(OfficeSurveyAudit officeSurveyAudit){
		return officeSurveyAuditDao.update(officeSurveyAudit);
	}

	@Override
	public OfficeSurveyAudit getOfficeSurveyAuditById(String id){
		return officeSurveyAuditDao.getOfficeSurveyAuditById(id);
	}

	@Override
	public Map<String, OfficeSurveyAudit> getOfficeSurveyAuditMapByIds(String[] ids){
		return officeSurveyAuditDao.getOfficeSurveyAuditMapByIds(ids);
	}

	@Override
	public List<OfficeSurveyAudit> getOfficeSurveyAuditList(){
		return officeSurveyAuditDao.getOfficeSurveyAuditList();
	}

	@Override
	public List<OfficeSurveyAudit> getOfficeSurveyAuditPage(Pagination page){
		return officeSurveyAuditDao.getOfficeSurveyAuditPage(page);
	}

	@Override
	public List<OfficeSurveyAudit> getOfficeSurveyAuditByUnitIdList(String unitId){
		return officeSurveyAuditDao.getOfficeSurveyAuditByUnitIdList(unitId);
	}

	@Override
	public List<OfficeSurveyAudit> getOfficeSurveyAuditByUnitIdPage(String unitId, Pagination page){
		return officeSurveyAuditDao.getOfficeSurveyAuditByUnitIdPage(unitId, page);
	}

	@Override
	public Map<String, OfficeSurveyAudit> getOfficeSurveyAuditByUnitIdMap(String unitId){
		return officeSurveyAuditDao.getOfficeSurveyAuditByUnitIdMap(unitId);
	}
	
	@Override
	public void save(OfficeSurveyAudit officeSurveyAudit,
			OfficeSurveyApply officeSurveyApply) {
		if(officeSurveyAudit!=null){
			officeSurveyAuditDao.save(officeSurveyAudit);
		}
		if(officeSurveyApply!=null){
			officeSurveyApplyDao.update(officeSurveyApply);
		}
	}
	@Override
	public OfficeSurveyAudit getOfficeSurveyAuditByUnitId(String unitId,
			String applyId) {
		return officeSurveyAuditDao.getOfficeSurveyAuditByUnitId(unitId, applyId);
	}
	
	public void setOfficeSurveyAuditDao(OfficeSurveyAuditDao officeSurveyAuditDao){
		this.officeSurveyAuditDao = officeSurveyAuditDao;
	}

	public void setOfficeSurveyApplyDao(OfficeSurveyApplyDao officeSurveyApplyDao) {
		this.officeSurveyApplyDao = officeSurveyApplyDao;
	}


}
