package net.zdsoft.office.survey.dao;

import java.util.*;

import net.zdsoft.office.survey.entity.OfficeSurveyApply;
import net.zdsoft.office.survey.entity.OfficeSurveyAudit;
import net.zdsoft.keel.util.Pagination;
/**
 * office_survey_audit
 * @author 
 * 
 */
public interface OfficeSurveyAuditDao{

	/**
	 * 新增office_survey_audit
	 * @param officeSurveyAudit
	 * @return
	 */
	public OfficeSurveyAudit save(OfficeSurveyAudit officeSurveyAudit);

	/**
	 * 根据ids数组删除office_survey_audit
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
	 * 根据unitId获取office_survey_audit列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSurveyAudit> getOfficeSurveyAuditByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_survey_audit获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSurveyAudit> getOfficeSurveyAuditByUnitIdPage(String unitId, Pagination page);
	
	public Map<String, OfficeSurveyAudit> getOfficeSurveyAuditByUnitIdMap(String unitId);
	
	public OfficeSurveyAudit getOfficeSurveyAuditByUnitId(String unitId,String applyId);

	
}