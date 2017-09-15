package net.zdsoft.office.dailyoffice.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeBusinessTrip;
/**
 * office_business_trip
 * @author 
 * 
 */
public interface OfficeBusinessTripDao{

	/**
	 * 新增office_business_trip
	 * @param officeBusinessTrip
	 * @return
	 */
	public OfficeBusinessTrip save(OfficeBusinessTrip officeBusinessTrip);

	/**
	 * 根据ids数组删除office_business_trip
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_business_trip
	 * @param officeBusinessTrip
	 * @return
	 */
	public Integer update(OfficeBusinessTrip officeBusinessTrip);

	/**
	 * 根据id获取office_business_trip
	 * @param id
	 * @return
	 */
	public OfficeBusinessTrip getOfficeBusinessTripById(String id);

	/**
	 * 根据ids数组查询office_business_tripmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeBusinessTrip> getOfficeBusinessTripMapByIds(String[] ids);

	/**
	 * 获取office_business_trip列表
	 * @return
	 */
	public List<OfficeBusinessTrip> getOfficeBusinessTripList();

	/**
	 * 分页获取office_business_trip列表
	 * @param page
	 * @return
	 */
	public List<OfficeBusinessTrip> getOfficeBusinessTripPage(Pagination page);

	/**
	 * 根据unitId获取office_business_trip列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeBusinessTrip> getOfficeBusinessTripByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_business_trip获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeBusinessTrip> getOfficeBusinessTripByUnitIdPage(String unitId, Pagination page);
	/**
	 * 根据状态查询所有office_business_trip列表
	 * @param unitId
	 * @param userId
	 * @param States
	 * @param page
	 * @return
	 */
	public List<OfficeBusinessTrip> getOfficeBusinessTripByUnitIdUserIdPage(String unitId,String userId,String States, Pagination page);
	/**
	 * 判断是否存在冲突
	 * @param applyUserId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public boolean isExistConflict(String id, String applyUserId, Date beginTime, Date endTime);
	
	/**
	 * 获取日期范围内的出差记录
	 * @param startTime
	 * @param endTime
	 * @param userIds
	 * @return
	 */
	public List<OfficeBusinessTrip> getListByStarttimeAndEndtime(Date startTime,Date endTime,String[] userIds);
	
	/**
	 * 获取当前日期出差记录
	 * @param unitId
	 * @param date
	 * @return
	 */
	public List<OfficeBusinessTrip> getListByUnitIdAndDate(String unitId,Date date);
	
	/**
	 * 根据ids数组查询
	 * @param ids
	 * @return
	 */
	public List<OfficeBusinessTrip> getOfficeBusinessTripByIds(String[] ids);
	
	public Map<String, OfficeBusinessTrip> getOfficeBusinessTripMapByFlowIds(
			String[] array);
	
	public List<OfficeBusinessTrip> HaveDoAudit(String userId,boolean invalid, Pagination page);
	
	public List<OfficeBusinessTrip> getOfficeBusinessTripsByUnitIdAndOthers(String unitId,String state,Date startTime,Date endTime,String applyUserName,Pagination page);
}