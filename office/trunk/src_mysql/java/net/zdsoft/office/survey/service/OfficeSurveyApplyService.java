package net.zdsoft.office.survey.service;

import java.util.*;

import net.zdsoft.office.survey.entity.OfficeSurveyApply;
import net.zdsoft.keel.util.Pagination;
/**
 * office_survey_apply
 * @author 
 * 
 */
public interface OfficeSurveyApplyService{

	/**
	 * 新增office_survey_apply
	 * @param officeSurveyApply
	 * @return
	 */
	public OfficeSurveyApply save(OfficeSurveyApply officeSurveyApply);

	/**
	 * 根据ids数组删除office_survey_apply数据
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
	 * 根据UnitId获取office_survey_apply列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSurveyApply> getOfficeSurveyApplyByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_survey_apply
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSurveyApply> getOfficeSurveyApplyByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据查询条件获取list
	 * @param unitId
	 * @param searchType 审核状态
	 * @param searchPlace 调研地点
	 * @param appayUserId 申请人ID
	 * @param searchName 申请人姓名
	 * @param searchStartTime 
	 * @param searchEndTime
	 * @param page
	 * @return
	 */
	public List<OfficeSurveyApply> getSurveyApplyListByConditions(String unitId, String searchType, String searchPlace, String appayUserId,
			String searchName, Date searchStartTime, Date searchEndTime, Pagination page);
	
	public List<OfficeSurveyApply> getSurveyApplyList(String userId, String unitId,
			String searchType, String searchPlace, Pagination page);
}