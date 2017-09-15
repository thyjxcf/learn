package net.zdsoft.office.dailyoffice.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeCarInfo;
/**
 * office_car_info
 * @author 
 * 
 */
public interface OfficeCarInfoDao{

	/**
	 * 新增office_car_info
	 * @param officeCarInfo
	 * @return
	 */
	public OfficeCarInfo save(OfficeCarInfo officeCarInfo);

	/**
	 * 单个删除车辆信息
	 * @param ids
	 * @return
	 */
	public void delete(String id);
	
	/**
	 * 根据ids数组删除office_car_info
	 * @param ids
	 * @return
	 */
	public void delete(String[] ids);

	/**
	 * 更新office_car_info
	 * @param officeCarInfo
	 * @return
	 */
	public Integer update(OfficeCarInfo officeCarInfo);

	/**
	 * 根据id获取office_car_info
	 * @param id
	 * @return
	 */
	public OfficeCarInfo getOfficeCarInfoById(String id);

	/**
	 * 根据ids数组查询office_car_infomap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeCarInfo> getOfficeCarInfoMapByIds(String[] ids);
	
	/**
	 * 根据单位id获取车辆map
	 * @param unitId
	 * @return
	 */
	public Map<String, OfficeCarInfo> getOfficeCarInfoMapByUnitId(String unitId);

	/**
	 * 获取office_car_info列表
	 * @return
	 */
	public List<OfficeCarInfo> getOfficeCarInfoList();

	/**
	 * 分页获取office_car_info列表
	 * @param page
	 * @return
	 */
	public List<OfficeCarInfo> getOfficeCarInfoPage(Pagination page);

	/**
	 * 根据unitId获取office_car_info列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeCarInfo> getOfficeCarInfoByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_car_info获取
	 * @param unitId
	 * @param carNumber TODO
	 * @param page
	 * @return
	 */
	public List<OfficeCarInfo> getOfficeCarInfoByUnitIdPage(String unitId, String carNumber, Pagination page);
}