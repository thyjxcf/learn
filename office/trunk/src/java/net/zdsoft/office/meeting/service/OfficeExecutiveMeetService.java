package net.zdsoft.office.meeting.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMeet;
/**
 * office_executive_meet
 * @author 
 * 
 */
public interface OfficeExecutiveMeetService{

	/**
	 * 新增office_executive_meet
	 * @param officeExecutiveMeet
	 * @return
	 */
	public OfficeExecutiveMeet save(OfficeExecutiveMeet officeExecutiveMeet);

	/**
	 * 根据ids数组删除office_executive_meet数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_executive_meet
	 * @param officeExecutiveMeet
	 * @return
	 */
	public Integer update(OfficeExecutiveMeet officeExecutiveMeet);
	
	/**
	 * 发布会议
	 * @param id
	 */
	public void publishMeet(String id);

	/**
	 * 根据id获取office_executive_meet
	 * @param id
	 * @return
	 */
	public OfficeExecutiveMeet getOfficeExecutiveMeetById(String id);

	/**
	 * 根据ids数组查询office_executive_meetmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeExecutiveMeet> getOfficeExecutiveMeetMapByIds(String[] ids);

	/**
	 * 获取office_executive_meet列表
	 * @return
	 */
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetList();

	/**
	 * 分页获取office_executive_meet列表
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetPage(Pagination page);

	/**
	 * 根据UnitId获取office_executive_meet列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_executive_meet
	 * @param unitId
	 * @param queryName
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetByUnitIdPage(String unitId, String queryName, String startTime, String endTime, Pagination page);
	
	/**
	 * 获取过期的会议，填写纪要
	 * @param unitId
	 * @param queryName
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetOverduePage(String unitId, String queryName, String startTime, String endTime, Pagination page);
	
	/**
	 * 获取我的会议列表
	 * @param unitId
	 * @param queryName
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveMeet> getMyMeetListPage(String unitId, String userId, String queryName, String startTime, String endTime, Pagination page);
}