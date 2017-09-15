package net.zdsoft.office.dailyoffice.dao;


import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeApplyNumber;
/**
 * office_apply_number
 * @author 
 * 
 */
public interface OfficeApplyNumberDao{

	/**
	 * 新增office_apply_number
	 * @param officeApplyNumber
	 * @return
	 */
	public OfficeApplyNumber save(OfficeApplyNumber officeApplyNumber);

	/**
	 * 根据ids数组删除office_apply_number
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 根据id获取office_apply_number
	 * @param id
	 * @return
	 */
	public OfficeApplyNumber getOfficeApplyNumberById(String id);

	/**
	 * 根据ids数组查询office_apply_numbermap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeApplyNumber> getOfficeApplyNumberMapByIds(String[] ids);

	/**
	 * 获取office_apply_number列表
	 * @return
	 */
	public List<OfficeApplyNumber> getOfficeApplyNumberList(String[] ids);

	/**
	 * 分页获取office_apply_number列表
	 * @param page
	 * @return
	 */
	public List<OfficeApplyNumber> getOfficeApplyNumberPage(Pagination page);

	/**
	 * 根据unitId获取office_apply_number列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeApplyNumber> getOfficeApplyNumberByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_apply_number获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeApplyNumber> getOfficeApplyNumberByUnitIdPage(String unitId, String userId, Date startTime,Date endTime,String roomType, String auditState, Pagination page);
	
	/**
	 * 获取待审核的数据
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param roomType
	 * @param searchSubject
	 * @param page
	 * @return
	 */
	public List<OfficeApplyNumber> getOfficeApplyNumberAuditPage(String unitId, Date startTime,Date endTime,String roomType, String auditState, String searchSubject, Pagination page);

	public void updatePass(String[] ids, String userId, Integer state);
	
	/**
	 * 审核
	 * @param id
	 * @param state
	 * @param remark
	 * @param userId
	 */
	public void update(String id,String state,String remark,String userId);
	
	/**
	 * 更新反馈信息
	 * @param officeApplyNumber
	 */
	public void updateFeedback(OfficeApplyNumber officeApplyNumber);
	
	public List<OfficeApplyNumber> getOfficeApplyNumbersByConditions(String unitId, Date startTime, Date endTime, String[] applyUserIds, String[] labInfoIds, Pagination page);
}