package net.zdsoft.office.dailyoffice.dao;

import java.util.List;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeCarApply;
/**
 * office_car_apply
 * @author 
 * 
 */
public interface OfficeCarApplyDao{

	/**
	 * 新增office_car_apply
	 * @param officeCarApply
	 * @return
	 */
	public OfficeCarApply save(OfficeCarApply officeCarApply);

	/**
	 * 根据id数组删除office_car_apply
	 * @param ids
	 * @return
	 */
	public Integer delete(String id);

	/**
	 * 更新office_car_apply
	 * @param officeCarApply
	 * @return
	 */
	public Integer update(OfficeCarApply officeCarApply);
	
	
	/**
	 * 更新office_car_apply
	 * @param officeCarApply
	 * @return
	 */
	public void updateState(OfficeCarApply officeCarApply);
	
	/**
	 * 更新加班时间
	 * @param officeCarApply
	 */
	public void updateOverTimeNumber(OfficeCarApply officeCarApply);
	
	/**
	 * 是否存在未使用的车辆记录
	 * @param carId
	 * @param driverId
	 * @return
	 */
	public boolean isExistUnUsed(String carId, String driverId);

	/**
	 * 根据id获取office_car_apply
	 * @param id
	 * @return
	 */
	public OfficeCarApply getOfficeCarApplyById(String id);

	/**
	 * 获取申请信息列表
	 * @param unitId
	 * @param applyUserId
	 * @param startTime
	 * @param endTime
	 * @param state
	 * @param page
	 * @return
	 */
	public List<OfficeCarApply> getOfficeCarListPage(String unitId, String applyUserId,
			String startTime, String endTime, String state, Pagination page);
	
	/**
	 * 获取审核列表
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param state
	 * @param driverId
	 * @param page
	 * @return
	 */
	public List<OfficeCarApply> getOfficeCarApplyByUnitIdPage(String unitId,
			String startTime, String endTime, String state, String driverId, Pagination page);
	
	/**
	 * 获取该车辆的后面使用情况
	 * @param carId
	 * @return
	 */
	public List<OfficeCarApply> getOfficeCarApplyByCarId(String carId);

	public List<OfficeCarApply> getOfficeCarApplyListByArea(String unitId,
			String driverId);
	
	public void updateApplyState(OfficeCarApply apply);
	/**
	 * 获取该车辆加班汇总
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param state
	 * @return
	 */
	public List<OfficeCarApply> getOfficeCarApplyByUnitIdStatePage(String unitId,
			String startTime, String endTime, String state,String driverId);
	
}