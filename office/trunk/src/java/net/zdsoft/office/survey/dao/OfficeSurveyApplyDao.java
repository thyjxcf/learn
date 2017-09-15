package net.zdsoft.office.survey.dao;

import java.util.*;

import net.zdsoft.office.survey.entity.OfficeSurveyApply;
import net.zdsoft.keel.util.Pagination;
/**
 * office_survey_apply
 * @author 
 * 
 */
public interface OfficeSurveyApplyDao{

	/**
	 * 新增office_survey_apply
	 * @param officeSurveyApply
	 * @return
	 */
	public OfficeSurveyApply save(OfficeSurveyApply officeSurveyApply);

	/**
	 * 根据ids数组删除office_survey_apply
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_survey_apply
	 * @param officeSurveyApply
	 * @return
	 */
	public Integer update(OfficeSurveyApply officeSurveyApply);

	/**
	 * 根据id获取office_survey_apply
	 * @param id
	 * @return
	 */
	public OfficeSurveyApply getOfficeSurveyApplyById(String id);

	/**
	 * 根据ids数组查询office_survey_applymap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeSurveyApply> getOfficeSurveyApplyMapByIds(String[] ids);

	/**
	 * 获取office_survey_apply列表
	 * @return
	 */
	public List<OfficeSurveyApply> getOfficeSurveyApplyList();

	/**
	 * 分页获取office_survey_apply列表
	 * @param page
	 * @return
	 */
	public List<OfficeSurveyApply> getOfficeSurveyApplyPage(Pagination page);

	/**
	 * 根据unitId获取office_survey_apply列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSurveyApply> getOfficeSurveyApplyByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_survey_apply获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSurveyApply> getOfficeSurveyApplyByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeSurveyApply> getSurveyApplyListByConditions(String unitId, String searchType, String searchPlace, String appayUserId,
			String searchName, Date searchStartTime, Date searchEndTime, Pagination page);
	
	public List<OfficeSurveyApply> getSurveyApplyList(String userId, String unitId,
			String applyStatus, String searchPlace, Pagination page);
}