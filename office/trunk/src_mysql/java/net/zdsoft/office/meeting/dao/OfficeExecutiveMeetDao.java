package net.zdsoft.office.meeting.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMeet;
/**
 * office_executive_meet
 * @author 
 * 
 */
public interface OfficeExecutiveMeetDao{

	/**
	 * 新增office_executive_meet
	 * @param officeExecutiveMeet
	 * @return
	 */
	public OfficeExecutiveMeet save(OfficeExecutiveMeet officeExecutiveMeet);

	/**
	 * 根据ids数组删除office_executive_meet
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
	 * 根据unitId获取office_executive_meet列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetByUnitIdList(String unitId);

	/**
	 * 查询会议列表
	 * @param unitId
	 * @param queryName
	 * @param startTime
	 * @param endTime
	 * @param state
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetByUnitIdPage(String unitId, String queryName, String startTime, String endTime, Integer state, Pagination page);
	
	/**
	 * 查询会议列表
	 * @param unitId
	 * @param queryName
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetOverduePage(String unitId, String queryName, String startTime, String endTime, Pagination page);
}