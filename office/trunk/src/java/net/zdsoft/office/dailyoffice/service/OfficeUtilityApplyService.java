package net.zdsoft.office.dailyoffice.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dto.AttendanceDto;
import net.zdsoft.office.dailyoffice.entity.OfficeUtilityApply;
/**
 * office_utility_apply
 * @author 
 * 
 */
public interface OfficeUtilityApplyService{

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
	public void addOfficeUtilityApplies(List<OfficeUtilityApply> officeUtilityApplies);
	
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
	 * 根据教室类型及申请日期获取已申请的教室信息
	 * @param roomType
	 * @param applyDate
	 * @param unitId
	 * @param userId
	 * @return
	 */
	
	public Map<String, OfficeUtilityApply> getApplyMap(String roomType,Date applyDate,String unitId,String userId);

	/**
	 * 根据ids数组删除office_utility_apply数据
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
	 * 分页获取office_utility_apply列表
	 * @param page
	 * @return
	 */
	public List<OfficeUtilityApply> getOfficeUtilityApplyPage(Pagination page);

	/**
	 * 根据UnitId获取office_utility_apply列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeUtilityApply> getOfficeUtilityApplyByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_utility_apply
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
	 * 获取课程id考勤需求的课程安排列表
	 * @param unitId
	 * @param courseId
	 * @return
	 */
	public List<AttendanceDto> getCourseArrange(String unitId, String courseId);
	
	/**
	 * 获取指定时间段的指定课程类型的考勤需求的所有课程安排列表
	 * @param unitId TODO
	 * @param startApplyDate
	 * @param endApplyDate
	 * @param studyType
	 * @return
	 */
	public List<AttendanceDto> getCourseArrange(String unitId, Date startApplyDate, Date endApplyDate, String studyType);
}