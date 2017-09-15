package net.zdsoft.office.survey.service;

import java.util.*;

import net.zdsoft.office.survey.entity.OfficeSurveyApply;
import net.zdsoft.office.survey.entity.OfficeSurveyAudit;
import net.zdsoft.keel.util.Pagination;
/**
 * office_survey_audit
 * @author 
 * 
 */
public interface OfficeSurveyAuditService{

	/**
	 * 新增office_survey_audit
	 * @param officeSurveyAudit
	 * @return
	 */
	public OfficeSurveyAudit save(OfficeSurveyAudit officeSurveyAudit);

	/**
	 * 根据ids数组删除office_survey_audit数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_survey_audit
	 * @param officeSurveyAudit
	 * @return
	 */
	public Integer update(OfficeSurveyAudit officeSurveyAudit);

	/**
	 * 根据id获取office_survey_audit
	 * @param id
	 * @return
	 */
	public OfficeSurveyAudit getOfficeSurveyAuditById(String id);

	/**
	 * 根据ids数组查询office_survey_auditmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeSurveyAudit> getOfficeSurveyAuditMapByIds(String[] ids);

	/**
	 * 获取office_survey_audit列表
	 * @return
	 */
	public List<OfficeSurveyAudit> getOfficeSurveyAuditList();

	/**
	 * 分页获取office_survey_audit列表
	 * @param page
	 * @return
	 */
	public List<OfficeSurveyAudit> getOfficeSurveyAuditPage(Pagination page);

	/**
	 * 根据UnitId获取office_survey_audit列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSurveyAudit> getOfficeSurveyAuditByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_survey_audit
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSurveyAudit> getOfficeSurveyAuditByUnitIdPage(String unitId, Pagination page);
	
	public Map<String, OfficeSurveyAudit> getOfficeSurveyAuditByUnitIdMap(String unitId);
	public void save(OfficeSurveyAudit officeSurveyAudit,OfficeSurveyApply officeSurveyApply);
	public OfficeSurveyAudit getOfficeSurveyAuditByUnitId(String unitId,String applyId);
}