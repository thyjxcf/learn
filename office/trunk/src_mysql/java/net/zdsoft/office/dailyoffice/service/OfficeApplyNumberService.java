package net.zdsoft.office.dailyoffice.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.subsystemcall.entity.StusysSectionTimeSetDto;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.dailyoffice.entity.OfficeApplyNumber;
import net.zdsoft.office.dailyoffice.entity.OfficeLabInfo;
/**
 * office_apply_number
 * @author 
 * 
 */
public interface OfficeApplyNumberService{

	/**
	 * 新增申请
	 * @param officeApplyNumber
	 * @param file
	 * @return
	 */
	public boolean saveComputerRoom(OfficeApplyNumber officeApplyNumber, Map<String, StusysSectionTimeSetDto> sstsMap);
	
	/**
	 * 新增申请
	 * @param officeApplyNumber
	 * @param sstsMap
	 * @return
	 */
	public boolean saveRoom(OfficeApplyNumber officeApplyNumber, Map<String, StusysSectionTimeSetDto> sstsMap);
	
	/**
	 * 新增申请
	 * @param officeApplyNumber
	 * @param file
	 * @return
	 */
	public boolean saveMeetingRoom(OfficeApplyNumber officeApplyNumber,UploadFile file);
	
	/**
	 * 实验室申请
	 * @param officeApplyNumber
	 * @param officeLabInfo
	 * @return
	 */
	public boolean saveLabRoom(OfficeApplyNumber officeApplyNumber, OfficeLabInfo officeLabInfo);
	
	/**
	 * 撤销申请
	 * @param officeApplyNumber
	 * @return
	 */
	public void cancel(OfficeApplyNumber officeApplyNumber, boolean flag, Map<String, StusysSectionTimeSetDto> sstsDtoList);

	/**
	 * 根据ids数组删除office_apply_number数据
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
	 * 分页获取office_apply_number列表
	 * @param page
	 * @return
	 */
	public List<OfficeApplyNumber> getOfficeApplyNumberPage(Pagination page);

	/**
	 * 根据UnitId获取office_apply_number列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeApplyNumber> getOfficeApplyNumberByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_apply_number
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeApplyNumber> getOfficeApplyNumberByUnitIdPage(String unitId, String userId, Date startTime,Date endTime,String roomType, String auditState,Pagination page);
	
	/**
	 * 获取需要审核的数据
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param roomType
	 * @param auditState TODO
	 * @param searchSubject
	 * @param page
	 * @return
	 */
	public List<OfficeApplyNumber> getOfficeApplyNumberAuditPage(String unitId, Date startTime,Date endTime,String roomType, String auditState, String searchSubject, Pagination page);
	
	/**
	 * 通过
	 * @param ids
	 * @param userId
	 * @param flag 是否需要同步到一卡通（镇海需求）
	 * @return
	 */
	public void pass(String[] ids,String userId, boolean flag);
	
	/**
	 * 审核
	 * @param id
	 * @param state
	 * @param remark
	 * @param userId
	 * @param flag 是否需要同步到一卡通（镇海需求）
	 */
	public void update(String id,String state,String remark,String userId, boolean flag);
	
	/**
	 * 更新反馈信息
	 * @param officeApplyNumber
	 */
	public void updateFeedback(OfficeApplyNumber officeApplyNumber);
	
	/**
	 * 根据条件查询实验申请记录
	 * @param unitId
	 * @param searchLabMode
	 * @param searchName
	 * @param searchSubject
	 * @param searchGrade
	 * @param searchUserName
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<OfficeApplyNumber> getOfficeApplyNumbersByConditions(String unitId, String searchLabMode, 
			String searchName, String searchSubject, String searchGrade, String searchUserName, Date startTime, Date endTime, Pagination page);
}