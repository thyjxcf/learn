package net.zdsoft.office.dailyoffice.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeUtilityApply;
/**
 * office_utility_apply
 * @author 
 * 
 */
public interface OfficeUtilityApplyDao{

	/**
	 * 新增office_utility_apply
	 * @param officeUtilityApply
	 * @return
	 */
	public void save(OfficeUtilityApply officeUtilityApply);
	
	/**
	 * 批量增加表信息
	 * @param officeUtilityApplies
	 */
	public void insertOfficeUtilityApplies(List<OfficeUtilityApply> officeUtilityApplies);
	
	/**
	 * 根据id更新状态
	 * @param ids
	 */
	public void updateStateByIds(String[] ids, Integer state);
	
	/**
	 * 判断是否已经被申请
	 * @param roomId
	 * @param period
	 * @param type
	 * @param userId
	 * @param date
	 * @return
	 */
	public boolean isApplyByOthers(String roomId, String period, String type, String userId, Date date);

	/**
	 * 删除那些未通过的
	 * @param officeUtilityApplies
	 */
	public void batchDelete(List<OfficeUtilityApply> officeUtilityApplies);
	
	/**
	 * 根据ids数组删除office_utility_apply
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_utility_apply
	 * @param officeUtilityApply
	 * @return
	 */
	public Integer update(OfficeUtilityApply officeUtilityApply);

	/**
	 * 根据id获取office_utility_apply
	 * @param id
	 * @return
	 */
	public OfficeUtilityApply getOfficeUtilityApplyById(String id);

	/**
	 * 根据ids数组查询office_utility_applymap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeUtilityApply> getOfficeUtilityApplyMapByIds(String[] ids);
	
	/**
	 * 根据ids数组查询office_utility_applys
	 * @param ids
	 * @return
	 */
	public List<OfficeUtilityApply> getOfficeUtilityApplyListByIds(String[] ids);

	/**
	 * 获取office_utility_apply列表
	 * @return
	 */
	public List<OfficeUtilityApply> getOfficeUtilityApplyList(String roomType,
			Date applyDate, String unitId, String userId);

	/**
	 * 分页获取office_utility_apply列表
	 * @param page
	 * @return
	 */
	public List<OfficeUtilityApply> getOfficeUtilityApplyPage(Pagination page);

	/**
	 * 根据unitId获取office_utility_apply列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeUtilityApply> getOfficeUtilityApplyByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_utility_apply获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeUtilityApply> getOfficeUtilityApplyByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 获取机房安排申请信息
	 * @param unitId
	 * @param type
	 * @param state
	 * @param courseId
	 * @return
	 */
	public List<OfficeUtilityApply> getOfficeUtilityApply(String unitId, String type, String state, String courseId);
	
	/**
	 * 获取机房安排申请信息
	 * @param unitId TODO
	 * @param type
	 * @param state
	 * @param startApplyDate
	 * @param endApplyDate
	 * @param studyType
	 * @return
	 */
	public List<OfficeUtilityApply> getOfficeUtilityApply(String unitId, String type, String state, Date startApplyDate, Date endApplyDate, String studyType);
}