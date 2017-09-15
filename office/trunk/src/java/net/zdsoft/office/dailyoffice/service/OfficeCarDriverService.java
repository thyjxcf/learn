package net.zdsoft.office.dailyoffice.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeCarDriver;
/**
 * office_car_driver
 * @author 
 * 
 */
public interface OfficeCarDriverService{

	/**
	 * 新增office_car_driver
	 * @param officeCarDriver
	 * @return
	 */
	public OfficeCarDriver save(OfficeCarDriver officeCarDriver);
	
	/**
	 * 批量更新驾驶员信息
	 * @param officeCarDrivers
	 */
	public void batchSave(List<OfficeCarDriver> officeCarDrivers);
	
	/**
	 * 批量更新驾驶员信息
	 * @param officeCarDrivers
	 */
	public void batchUpdate(List<OfficeCarDriver> officeCarDrivers);

	/**
	 * 根据ids数组删除office_car_driver数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_car_driver
	 * @param officeCarDriver
	 * @return
	 */
	public Integer update(OfficeCarDriver officeCarDriver);

	/**
	 * 根据id获取office_car_driver
	 * @param id
	 * @return
	 */
	public OfficeCarDriver getOfficeCarDriverById(String id);

	/**
	 * 根据ids数组查询office_car_drivermap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeCarDriver> getOfficeCarDriverMapByIds(String[] ids);
	
	/**
	 * 根据单位id获取驾驶员map
	 * @param unitId
	 * @return
	 */
	public Map<String, OfficeCarDriver> getOfficeCarDriverMapByUnitId(String unitId);

	/**
	 * 获取office_car_driver列表
	 * @return
	 */
	public List<OfficeCarDriver> getOfficeCarDriverList();

	/**
	 * 分页获取office_car_driver列表
	 * @param page
	 * @return
	 */
	public List<OfficeCarDriver> getOfficeCarDriverPage(Pagination page);

	/**
	 * 根据UnitId获取office_car_driver列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeCarDriver> getOfficeCarDriverByUnitIdList(String unitId);
	
	public List<OfficeCarDriver> getOfficeCarDriverWithDelByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_car_driver
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeCarDriver> getOfficeCarDriverByUnitIdPage(String unitId, Pagination page);
}