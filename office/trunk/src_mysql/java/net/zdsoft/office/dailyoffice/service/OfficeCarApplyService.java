package net.zdsoft.office.dailyoffice.service;

import java.util.List;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dto.OfficeCarApplyDto;
import net.zdsoft.office.dailyoffice.dto.UseCarInfoDto;
import net.zdsoft.office.dailyoffice.entity.OfficeCarApply;
/**
 * office_car_apply
 * @author 
 * 
 */
public interface OfficeCarApplyService{

	/**
	 * 新增office_car_apply
	 * @param officeCarApply
	 * @return
	 */
	public OfficeCarApply save(OfficeCarApply officeCarApply);

	/**
	 * 根据ids数组删除office_car_apply数据
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
	 * 更新审核状态
	 * @param id
	 * @param applyState
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
	 * 获取车辆使用信息
	 * @param carId
	 * @return
	 */
	public UseCarInfoDto getCarUseInfo(String carId);

	/**
	 * 获取申请列表
	 * @param unitId
	 * @param applyUserId
	 * @param startTime
	 * @param endTime
	 * @param state
	 * @param page
	 * @return
	 */
	public List<OfficeCarApply> getOfficeCarApplyListPage(String unitId, String applyUserId, String startTime, String endTime, String state, Pagination page);
	
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
	public List<OfficeCarApply> getOfficeCarApplyByUnitIdPage(String unitId, String startTime, String endTime, String state, String driverId, Pagination page);
	
	/**
	 * 获取审核最终通过的记录
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param page TODO
	 * @return
	 */
	public List<OfficeCarApply> getOfficeCarPassList(String unitId, String startTime, String endTime, Pagination page);
	
	/**
	 * 车辆汇总情况查询
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param querySumType 1：车辆，2：驾驶员
	 * @return
	 */
	public List<UseCarInfoDto> getOfficeCarUseInfoList(String unitId, String startTime, String endTime, String querySumType);
	
	/**
	 * 获取组装好的导出数据
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<UseCarInfoDto> getOfficeCarPassSum(String unitId, String startTime, String endTime);

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
	public List<OfficeCarApplyDto> getOfficeCarApplyByUnitIdStatePage(String unitId,
			String startTime, String endTime, String state,String driverId);
}