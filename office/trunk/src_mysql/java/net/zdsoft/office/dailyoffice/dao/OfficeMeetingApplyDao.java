package net.zdsoft.office.dailyoffice.dao;

import java.util.*;

import net.zdsoft.office.dailyoffice.entity.OfficeMeetingApply;
import net.zdsoft.keel.util.Pagination;
/**
 * office_meeting_apply
 * @author 
 * 
 */
public interface OfficeMeetingApplyDao{

	/**
	 * 新增office_meeting_apply
	 * @param officeMeetingApply
	 * @return
	 */
	public OfficeMeetingApply save(OfficeMeetingApply officeMeetingApply);

	/**
	 * 根据ids数组删除office_meeting_apply
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_meeting_apply
	 * @param officeMeetingApply
	 * @return
	 */
	public Integer update(OfficeMeetingApply officeMeetingApply);

	/**
	 * 根据id获取office_meeting_apply
	 * @param id
	 * @return
	 */
	public OfficeMeetingApply getOfficeMeetingApplyById(String id);

	/**
	 * 根据ids数组查询office_meeting_applymap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeMeetingApply> getOfficeMeetingApplyMapByIds(String[] ids);

	/**
	 * 获取office_meeting_apply列表
	 * @return
	 */
	public List<OfficeMeetingApply> getOfficeMeetingApplyList();
	
	/**
	 * 根据单位ID及查询条件（可为空）分页获取office_meeting_apply列表
	 * @param unitId
	 * @param applyUserId
	 * @param queryName
	 * @param queryState
	 * @param queryBeginDate
	 * @param queryEndDate
	 * @param page
	 * @return
	 */
	public List<OfficeMeetingApply> getOfficeMeetingApplyList(String unitId, String applyUserId, 
			String queryName, String[] queryState, String queryBeginDate, String queryEndDate, Pagination page);

	/**
	 * 分页获取office_meeting_apply列表
	 * @param page
	 * @return
	 */
	public List<OfficeMeetingApply> getOfficeMeetingApplyPage(Pagination page);

	/**
	 * 根据unitId获取office_meeting_apply列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeMeetingApply> getOfficeMeetingApplyByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_meeting_apply获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeMeetingApply> getOfficeMeetingApplyByUnitIdPage(String unitId, Pagination page);
	
}